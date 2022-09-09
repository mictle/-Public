package project.tools;

import project.tools.*;
import project.GameManager;
import project.objects.*;

public class ColliderDitect{

	public Vector2 getAbsPhysld(CharacterData c){
		Vector2 ld;
		ld =new Vector2(c.getPhyslu().x, c.getPhysrd().y);
		ld.add(c.getCenterPos(), ld);
		return ld;
	}

	public Vector2 getAbsPhysru(CharacterData c){
		Vector2 ru;
		ru =new Vector2(c.getPhysrd().x, c.getPhyslu().y);
		ru.add(c.getCenterPos(), ru);
		return ru;
	}

	public Vector2 getAbsPhyslu(CharacterData c){
		Vector2 lu;
		lu = new Vector2();
		lu.add(c.getCenterPos(), c.getPhyslu());
		return lu;
	}

	public Vector2 getAbsPhysrd(CharacterData c){
		Vector2 rd;
		rd = new Vector2();
		rd.add(c.getCenterPos(), c.getPhysrd());
		return rd;
	}

	public boolean isBlock(Vector2 v){
		return (Stage.block[(int)v.x/64][(int)v.y/64]!=0);
	}

    public boolean onFloorDitect(CharacterData c){
		Vector2 ld, rd;
		int ldPosX, ldPosYd, rdPosX, rdPosYd;

		ld = getAbsPhysld(c);
		rd = getAbsPhysrd(c);

		rdPosX=(int)(rd.x/64);  rdPosYd=(int)((rd.y+2)/64);
		ldPosX=(int)(ld.x/64);  ldPosYd=(int)((ld.y+2)/64);
		for(int i=ldPosX;i<=rdPosX;i++){
			if(Stage.block[i][ldPosYd]>0){
				return true;
			}
		}
		return false;
		/*
		if(Stage.block.get(rdPosY).get(rdPosX)>0 || Stage.block.get(ldPosY).get(ldPosX)>0){
			System.out.println(ldPosX + "," + ldPosY + "," + rdPosX + "," + rdPosY);
			return true;
		}else{
			return false;
		}
		*/
	}

	public boolean onWallDitectR(CharacterData c){
		Vector2 rd, ru;
		int rdPosXd, rdPosY, ruPosXd, ruPosY;

		rd = getAbsPhysrd(c);
		ru = getAbsPhysru(c);

		rdPosXd=(int)((rd.x+5)/64);  rdPosY=(int)(rd.y/64);
		ruPosXd=(int)((ru.x+5)/64);  ruPosY=(int)(ru.y/64);
		for(int i=ruPosY;i<=rdPosY;i++){
			if(Stage.block[ruPosXd][i]>0){
				return true;
			}
		}
		return false;

	}

	public boolean onWallDitectL(CharacterData c){
		Vector2 ld, lu;
		int ldPosXd, ldPosY, luPosXd, luPosY;

		ld = getAbsPhysld(c);
		lu = getAbsPhyslu(c);

		ldPosXd=(int)((ld.x-5)/64);  ldPosY=(int)(ld.y/64);
		luPosXd=(int)((lu.x-5)/64);  luPosY=(int)(lu.y/64);
		for(int i=luPosY;i<=ldPosY;i++){
			if(Stage.block[luPosXd][i]>0){
				return true;
			}
		}
		return false;

	}

	public boolean onCeilingDitect(CharacterData c){
		Vector2 lu, ru;
		int luPosX, luPosYd, ruPosX, ruPosYd;

		lu = getAbsPhyslu(c);
		ru = getAbsPhysru(c);

		ruPosX=(int)(ru.x/64);  ruPosYd=(int)((ru.y-2)/64);
		luPosX=(int)(lu.x/64);  luPosYd=(int)((lu.y-2)/64);
		for(int i=luPosX;i<=ruPosX;i++){
			if(Stage.block[i][luPosYd]>0){
				return true;
			}
		}
		return false;

	}

	public CharacterData enemyDitect(){
		int rLefter,lRighter,uDowner,dUpper;
		CharacterData p = GameManager.charaList.get(0);
		for(CharacterData c : GameManager.charaList){
			if(c.getId() != p.getId()){
				if(getAbsPhysrd(p).x > getAbsPhysrd(c).x){//右端のより左側=x座標小
					rLefter = (int)getAbsPhysrd(c).x;
				}else{
					rLefter = (int)getAbsPhysrd(p).x;
				}
				if(getAbsPhyslu(p).x < getAbsPhyslu(c).x){//左端のより右側=x座標大
					lRighter = (int)getAbsPhyslu(c).x;
				}else{
					lRighter = (int)getAbsPhyslu(p).x;
				}
				if(getAbsPhyslu(p).y < getAbsPhyslu(c).y){//上端のより下側=y座標大
					uDowner = (int)getAbsPhyslu(c).y;
				}else{
					uDowner = (int)getAbsPhyslu(p).y;
				}
				if(getAbsPhysrd(p).y > getAbsPhysrd(c).y){//下端のより上側=x座標小
					dUpper = (int)getAbsPhysrd(c).y;
				}else{
					dUpper = (int)getAbsPhyslu(p).y;
				}
				if(lRighter < rLefter && uDowner < dUpper && c.gethp() != 0){
					return c;
				}
			}
		}
		return p;//当たらなければプレイヤー自身を返す
	}


	public CharacterData attackToEnemy(Vector2 attDif1, Vector2 attDif2, int attack){
		CharacterData p=GameManager.charaList.get(0);
		Vector2 attLu, attRd;
		attLu = new Vector2();   attRd = new Vector2();
		//attDif1 = new Vector2(25,-30);  attDif2 = new Vector2(80,20);
		attLu.add(p.getCenterPos(),attDif1);
		attRd.add(p.getCenterPos(),attDif2);
		int rLefter,lRighter,uDowner,dUpper;

		for(CharacterData c : GameManager.charaList){
			if(attRd.x > getAbsPhysrd(c).x){//右端のより左側=x座標小
				rLefter = (int)getAbsPhysrd(c).x;
			}else{
				rLefter = (int)attRd.x;
			}
			if(attLu.x < getAbsPhyslu(c).x){//左端のより右側=x座標大
				lRighter = (int)getAbsPhyslu(c).x;
			}else{
				lRighter = (int)attLu.x;
			}
			if(attLu.y < getAbsPhyslu(c).y){//上端のより下側=y座標大
				uDowner = (int)getAbsPhyslu(c).y;
			}else{
				uDowner = (int)attLu.y;
			}
			if(attRd.y > getAbsPhysrd(c).y){//下端のより上側=x座標小
				dUpper = (int)getAbsPhysrd(c).y;
			}else{
				dUpper = (int)attRd.y;
			}
			if(c.getId()!=0 && lRighter < rLefter && uDowner < dUpper && c.getcoolTimeDmg() == 0 && c.getcoolTimeDie()==0){
				System.out.println("hit");
				c.damage(attack);
				return c;
			}
		}
		return p;//当たらなければプレイヤー自身を返す
	}


	public boolean bulletDitect(Bullet b){
		int rLefter,lRighter,uDowner,dUpper;
		CharacterData p = GameManager.charaList.get(0);
			if(b.getId() != p.getId()){
				if(getAbsPhysrd(p).x > getAbsPhysrd(b).x){//右端のより左側=x座標小
					rLefter = (int)getAbsPhysrd(b).x;
				}else{
					rLefter = (int)getAbsPhysrd(p).x;
				}
				if(getAbsPhyslu(p).x < getAbsPhyslu(b).x){//左端のより右側=x座標大
					lRighter = (int)getAbsPhyslu(b).x;
				}else{
					lRighter = (int)getAbsPhyslu(p).x;
				}
				if(getAbsPhyslu(p).y < getAbsPhyslu(b).y){//上端のより下側=y座標大
					uDowner = (int)getAbsPhyslu(b).y;
				}else{
					uDowner = (int)getAbsPhyslu(p).y;
				}
				if(getAbsPhysrd(p).y > getAbsPhysrd(b).y){//下端のより上側=x座標小
					dUpper = (int)getAbsPhysrd(b).y;
				}else{
					dUpper = (int)getAbsPhyslu(p).y;
				}
				if(lRighter < rLefter && uDowner < dUpper && c.gethp() != 0){
					return true;
				}
			}
		return false;
	}

}
