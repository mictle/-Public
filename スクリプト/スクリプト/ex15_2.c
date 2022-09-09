#include<stdio.h>
#define SETMAX 10600

struct set{int elements[SEETMAX]; int size;};

int delete_min_int(struct set *p){
  
}

void print_set(struct set *p){
  int i;
  printf("(");
  for(i=0;i<p->size;i++) printf(" %d",p->elements[i]);
  printf(" }\n");
}

int main(){
  int i,m;
  struct set s;
  i=0;
  while(fgets(buf,sizeof(buf),stdin)!=NULL){
    sscanf(buf,"%d ",&s.elements[i]);
    i++;
  }
  s.size=i;
  while(i){
    m=delete_min_int(&s);
    if(m<0) break;
    printf("%d : ",m); print_set(&s);
  }

  return 0;
}
