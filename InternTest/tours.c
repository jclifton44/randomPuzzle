#include <stdio.h>
#include <stdlib.h>
#include "tours.h"
#include <time.h>
//#define DEBUG 1
//has a limit of 99X99


Grid* generateGrid(int x, int y, int x_start, int y_start, int x_end, int y_end);
void* errorCheckedMalloc(int x);
int calculatePaths(Grid* grid, Node* currentNode);
//char checkCovered(Grid* grid);
int numCovered = 0;

int numberOfPaths;



int main(void){
	#ifdef DEBUG
	printf("This is a test\n");
	#endif
	int x;
	int y;
 	printf("Only works for grids with heights and widths < 99\n\n");
 	printf("Enter a Height: ");
 	scanf("%d",&y);
	printf("Enter a Width: ");
	scanf("%d",&x);
	clock_t start = clock();	
	
	Grid* theGrid = generateGrid(x,y,1,1,1,y);
	calculatePaths(theGrid, theGrid->Nodes[0][0]);
	printf("The number of possible paths: %d\n", numberOfPaths);
	
	printf("Time elapsed: %fs\n", ((double)clock() - start) / CLOCKS_PER_SEC);



return 0;
}

Grid* generateGrid(int x, int y, int x_start, int y_start, int x_end, int y_end){
	int i,j;
	if(x>99||y>99){
		printf("Cannot make grid length or width larger than 99. Exiting..\n");
		exit(1);
	}

	x_start--;
	y_start--;//Zero index each
	x_end--;
	y_end--;
	Grid* my_grid = (Grid*) errorCheckedMalloc(sizeof(Grid));
	my_grid->length = y;
	my_grid->width = x;	
	//my_grid->Nodes = errorCheckedMalloc(x*y*sizeof(Node*));
	//void* blah = malloc(2);	
	for(j=0;j<y;j++){
		for(i=0;i<x;i++){
			my_grid->Nodes[j][i] = errorCheckedMalloc(sizeof(Node));
			my_grid->Nodes[j][i]->row = j;
			my_grid->Nodes[j][i]->col = i;
			my_grid->Nodes[j][i]->traveledTo = FALSE;
			my_grid->Nodes[j][i]->place = OTHER;
			if(i==x_start && j==y_start){
				my_grid->Nodes[j][i]->traveledTo = TRUE;		
				my_grid->Nodes[j][i]->place = START;		
			}
			if(i==x_end && j==y_end)
				my_grid->Nodes[j][i]->place = END;
		}
	}
	for(j=0;j<y;j++){
		for(i=0;i<x;i++){
			if(j>0)
				my_grid->Nodes[j][i]->up = my_grid->Nodes[j-1][i];
			if(j==0)
				my_grid->Nodes[j][i]->up = NULL;
			
			if(j<(y-1))
				my_grid->Nodes[j][i]->down = my_grid->Nodes[j+1][i];
			if(j==(y-1))
				my_grid->Nodes[j][i]->down = NULL;

			if(i>0)	
				my_grid->Nodes[j][i]->left = my_grid->Nodes[j][i-1];
			if(i==0)	
				my_grid->Nodes[j][i]->left = NULL;

			if(i<(x-1))
				my_grid->Nodes[j][i]->right = my_grid->Nodes[j][i+1];
			if(i==(x-1))
				my_grid->Nodes[j][i]->right = NULL;
				
		}
	}




return my_grid;
}

/*char checkCovered(Grid* grid){

	int i;
	int j;
	for(j=0;j<grid->length;j++){
		for(i=0;i<grid->width;i++){
			if(grid->Nodes[j][i]->traveledTo==0)
				return 0;
		}
	}
	return 1;


}NOT USED*/	


int calculatePaths(Grid* grid, Node* currentNode){
	numCovered++;
	currentNode->traveledTo=TRUE;
	if(numCovered==grid->length*grid->width && currentNode->place==END){
		numberOfPaths++;

	}
	if(currentNode->up && !currentNode->up->traveledTo)
		calculatePaths(grid,currentNode->up);			
	if(currentNode->down && !currentNode->down->traveledTo)
		calculatePaths(grid,currentNode->down);
	if(currentNode->left && !currentNode->left->traveledTo)
		calculatePaths(grid,currentNode->left);			
	if(currentNode->right && !currentNode->right->traveledTo)
		calculatePaths(grid,currentNode->right);		
		
	currentNode->traveledTo=FALSE;	
	numCovered--;

}

void* errorCheckedMalloc(int x){

	void* ptr = malloc(x);
	if(ptr==0){
		printf("Out of memory Cannot malloc. Exiting\n");
		exit(1);
	}
	return ptr;

}
