#include<stdio.h>
#define SETMAX 10600

struct set{int elements[SEETMAX]; int size;};

void init_set(struct set *p, int n, int e){
  p->size = n-1;
  int i,num=0;
  for(i=0;i<n-1;i++){
    if(i!=e) p->elements[num]=i;
    num++;
  }
}

void print_set(struct set *p){
  int i;
  printf("(");
  for(i=0;i<p->size;i++) printf(" %d",p->elements[i]);
  printf(" }\n");
}

int main(){
  int n,e;
  struct set s;
  scanf("%d,%d ", &n, &e);
  init_set(&s, n, e);
  print_set(&s);
  return 0;
}
