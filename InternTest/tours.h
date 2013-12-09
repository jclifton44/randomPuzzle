//Jeremy Clifton
//intern puzzle
//tours.h
#define TRUE 1;
#define FALSE 0;



enum location { 
		START,
		END,
		OTHER
};

typedef struct Node{
	enum location place;
	int row;
	int col;
	char traveledTo;
	struct Node* up;
	struct Node* down;
	struct Node* left;
	struct Node* right;
}Node;


typedef struct{
	Node* location;
}Marker;

typedef struct Grid{
	int length;
	int width;
	struct Node* Nodes[99][99]; // Equivilant to Node* Nodes[i][j] where i is col#-1 and j is row#-1
			// Node*** Makes it more dynamic.
	
}Grid;




