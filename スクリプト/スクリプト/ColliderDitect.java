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

    public boolean onFloorDitect(CharacterData c){
		Vector2 ld, rd;
		int ldPosX, ldPosYd, rdPosX, rdPosYd;

		ld = getAbsPhysld(c);
		rd = getAbsPhysrd(c);

		rdPosX=(int)(rd.x/64);  rdPosYd=(int)((rd.y+2)/64);
		ldPosX=(int)(ld.x/64);  ldPosYd=(int)((ld.y+2)/64);
		for(int i=ldPosX;i<=rdPosX;i++){
			if(Stage.block.get(ldPosYd).get(i)>0){
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

		rdPosXd=(int)((rd.x+2)/64);  rdPosY=(int)(rd.y/64);
		ruPosXd=(int)((ru.x+2)/64);  ruPosY=(int)(ru.y/64);
		for(int i=ruPosY;i<=rdPosY;i++){
			if(Stage.block.get(i).get(ruPosXd)>0){
				return true;
			}
		}
		return false;

		/*
		if(Stage.block.get(rdPosY).get(rdPosX)>0 || Stage.block.get(ruPosY).get(ruPosX)>0){
			return true;
		}else{
			return false;
		}
		*/
	}

	public boolean onWallDitectL(CharacterData c){
		Vector2 ld, lu;
		int ldPosXd, ldPosY, luPosXd, luPosY;

		ld = getAbsPhysld(c);
		lu = getAbsPhyslu(c);

		ldPosXd=(int)((ld.x-2)/64);  ldPosY=(int)(ld.y/64);
		luPosXd=(int)((lu.x-2)/64);  luPosY=(int)(lu.y/64);
		for(int i=luPosY;i<=ldPosY;i++){
			if(Stage.block.get(i).get(luPosXd)>0){
				return true;
			}
		}
		return false;

		/*
		if(Stage.block.get(ldPosY).get(ldPosX)>0 || Stage.block.get(luPosY).get(luPosX)>0){
			return true;
		}else{
			return false;
		}
		*/
	}

	public boolean onCeilingDitect(CharacterData c){
		Vector2 lu, ru;
		int luPosX, luPosYd, ruPosX, ruPosYd;

		lu = getAbsPhyslu(c);
		ru = getAbsPhysru(c);

		ruPosX=(int)(ru.x/64);  ruPosYd=(int)((ru.y-2)/64);
		luPosX=(int)(lu.x/64);  luPosYd=(int)((lu.y-2)/64);
		for(int i=luPosX;i<=ruPosX;i++){
			if(Stage.block.get(luPosYd).get(i)>0){
				return true;
			}
		}
		return false;
		/*
		if(Stage.block.get(ruPosY).get(ruPosX)>0 || Stage.block.get(luPosY).get(luPosX)>0){
			return true;
		}else{
			return false;
		}
		*/
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
				if(getAbsPhyslu(p).x < getAbsPhyslu(c).x){//上端のより下側=y座標大
					uDowner = (int)getAbsPhyslu(c).x;
				}else{
					uDowner = (int)getAbsPhyslu(p).x;
				}
				if(getAbsPhysrd(p).x > getAbsPhysrd(c).x){//下端のより上側=x座標小
					dUpper = (int)getAbsPhysrd(c).x;
				}else{
					dUpper = (int)getAbsPhyslu(p).x;
				}
				if(lRighter < rLefter && uDowner < dUpper){
					return c;
				}
			}
		}
		return p;
	}

	/*
	public CharacterData attackToEnemy(){

	}
	*/
}
