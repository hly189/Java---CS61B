package player;

import list.DList;
import list.DListNode;
import list.InvalidNodeException;
import list.List;

class Board {
    int[][] board;
    int chipcount;
    int mpColor;
    int oppColor;
    int score;

    public Board() {
        chipcount = 0;
        score = 0;
        board = new int[8][8];
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                board[x][y] = -1;
    }

    public Board(int MPcolor) {
        chipcount = 0;
        mpColor = MPcolor;
        oppColor = 1 - MPcolor;
        score = 0;
        board = new int[8][8];
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                board[x][y] = -1;
    }

    public boolean hasneighbor(int x, int y, int color) {
        int neighbors = 0;
        int minx = x - 1, maxx = x + 1, miny = y - 1, maxy = y + 1;
        if (minx < 0)
            minx = 0;
        if (maxx > 7)
            maxx = 7;
        if (miny < 0)
            miny = 0;
        if (maxy > 7)
            maxy = 7;
        for (int X2 = minx; X2 <= maxx; X2++) {
            for (int Y2 = miny; Y2 <= maxy; Y2++) {
                if (X2 != x || Y2 != y) {
                    if (board[X2][Y2] == color) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean clustermove(int x, int y, int color) {
        int neighbors = 0;
        int minx = x - 1, maxx = x + 1, miny = y - 1, maxy = y + 1;
        if (minx < 0)
            minx = 0;
        if (maxx > 7)
            maxx = 7;
        if (miny < 0)
            miny = 0;
        if (maxy > 7)
            maxy = 7;
        for (int X2 = minx; X2 <= maxx; X2++)
            for (int Y2 = miny; Y2 <= maxy; Y2++)
                if (X2 != x || Y2 != y)
                    if (board[X2][Y2] == color) {
                        neighbors++;
                        if (neighbors >= 2)
                            return false;
                        if (hasneighbor(X2, Y2, color))
                            return false;
                    }
        return true;

    }

    public boolean validMove(Move m, int color) {
    	if( m == null)
		return false;
        if (m.moveKind == Move.ADD) {
            if (board[m.x1][m.y1] != -1)
                return false;

            if (color == 0)
                if (m.x1 == 0 || m.x1 == 7)
                    return false;
            if (color == 1)
                if (m.y1 == 0 || m.y1 == 7)
                    return false;

            if (chipcount >= 20)
                return false;
            if (!clustermove(m.x1, m.y1, color))
                return false;
        }
        if (m.moveKind == Move.STEP) {
            if (chipcount < 20)
                return false;

            if (board[m.x2][m.y2] != color)
                return false;
            if (board[m.x1][m.y1] != -1)
                return false;

            if (color == 0)
                if (m.x1 == 0 || m.x1 == 7)
                    return false;
            if (color == 1)
                if (m.y1 == 0 || m.y1 == 7)
                    return false;
            board[m.x2][m.y2] = -1;
            if (clustermove(m.x1, m.y1, color)) {
                board[m.x2][m.y2] = color;
                return true;
            } else {
                board[m.x2][m.y2] = color;
                return false;
            }

        }
        return true;
    }

    boolean setColor(int x, int y, int color) {
        board[x][y] = color;
        return true;
    }

    boolean move(Move m, int color) {
        if (!validMove(m, color))
            return false;
        if (m.moveKind == Move.ADD) {
            board[m.x1][m.y1] = color;
            chipcount++;
        }
        if (m.moveKind == Move.STEP) {
            board[m.x2][m.y2] = -1;
            board[m.x1][m.y1] = color;
        }
        return true;

    }

    boolean undoMove(Move m) {
        if (m.moveKind == Move.ADD) {
            board[m.x1][m.y1] = -1;
            chipcount--;
        }
        if (m.moveKind == Move.STEP) {
            board[m.x2][m.y2] = -1;
            board[m.x1][m.y1] = -1;
        }
        return true;
    }

    DList validMoves(int color) {
        int x1, y1, x2, y2;
        Move m;
        DList L = new DList();
        if (!full()) {
            for (x1 = 0; x1 < 8; x1++)
                for (y1 = 0; y1 < 8; y1++) {
                    m = new Move(x1, y1);
                    if (validMove(m, color))
                        L.insertFront(m);

                }
        } else {
            for (x2 = 0; x2 < 8; x2++)
                for (y2 = 0; y2 < 8; y2++)
                    if (board[x2][y2] == color)
                        for (x1 = 0; x1 < 8; x1++)
                            for (y1 = 0; y1 < 8; y1++) {
                                m = new Move(x1, y1, x2, y2);
                                if (validMove(m, color))
                                    L.insertFront(m);
                            }
        }
        return L;
    }

    boolean full() {
        return chipcount >= 20;
    }





  //this function returns true if 2 spaces (x1,y1) and (x2,y2) are connected horizontally, vertically or diagonally by empty spaces.
  boolean connected(int x1, int y1, int x2, int y2){
  
  	if(x2 == x1)
		if ( x2 == 0 || x2 == 7)
			return false;
	if(y2 == y1 )
		if (y2 == 0 || y2 == 7)
			return false;
  	int ypos,xpos;
  	if (x1==x2){
		for(ypos = Math.min(y1,y2)+1;ypos <Math.max(y1,y2);ypos++)
			if(board[x1][ypos]!= -1)
				return false;
		return true;
	}
	
  	if (y1==y2){
		for(xpos = Math.min(x1,x2)+1;xpos <Math.max(x1,x2);xpos++)
			if(board[xpos][y1]!= -1)
				return false;
		return true;
	}
	if(y2-y1 == x2-x1){
		ypos=Math.min(y1,y2)+1;
		for(xpos = Math.min(x1,x2)+1;xpos<Math.max(x1,x2);xpos++){
			if(board[xpos][ypos] != -1)
				return false;
			ypos++;
		}
		return true;
	}
	
	
	if(y2-y1 == x1-x2){
		ypos=Math.max(y1,y2)-1;
		for(xpos = Math.min(x1,x2)+1;xpos<Math.max(x1,x2);xpos++){
			if(board[xpos][ypos] != -1)
				{ return false;}
			ypos--;
		}
		return true;
	}
	return false;
  }
  
 
  //calculates the difference in one colors strength over the oponent with weight on the oponents connections so we prefer blocking over connecting.
  int evaluate(int color){
 	int positions = 0,N,M;
	if(color == 0 )
	{
		for(N=0;N<8;N++)
			for(M=1;M<7;M++)
				if(board[N][M]== color)
					positions+=N*N;
	}
	else
	{
		for(N=0;N<8;N++)
			for(M=1;M<7;M++)
				if(board[M][N]== color)
					positions+=N*N;
	}
  	return testcompletion(color) - testcompletion(1-color)+positions;
  }
  
  

	/// we test the completion of a given color recursively   played is the number of chips played by the recursive call calling this
	// remaining is the list of all remaining chips that hve not yet been played in this recursion.. depth is the max recursion depth
	// color is the color whos network we are trying to find.
    public int testcompletion(DList played, DList remaining, int depth,int color, int zone){
    	int max=played.length();



    	try{


    	int [] lastchip = (int[])played.front().item();
			
		
int completeness=0;
	max = played.length()*(lastchip[0]+1);
	if(color == 0)
		max = played.length()*(lastchip[1]+1);
	DListNode dl = remaining.front();
	int remainingl = remaining.length();
	int[] nextchip;

	if(played.length()>=6)// if we've connected enough chips
		if(lastchip[0]==7 || lastchip[1]==7){

		 	return 100000000;
			}
    	else
        	if(lastchip[0]==7 || lastchip[1]==7)
           	 return 10000;
	if(depth <= 0 )
			return played.length();
	//for each remaining chip
	for(int N=0;N<remainingl;N++){
		
		nextchip = (int[])dl.item();
		dl=dl.next();
		// if we are connected and not entering a goal twice.
		if(	connected(lastchip[0],lastchip[1],nextchip[0],nextchip[1])
			&& nextchip[0]!=0&&nextchip[1]!=0&&lastchip[0]!=7&&lastchip[1]!=7	)
		{
		
			//let us test to see if we are going in same direction twice(this is invalid for nework)
			boolean validcon=false;
			//if we started in a start-goal we cant be as start goals are never in remainign chips
			if(lastchip[0]==0 || lastchip[1]==0)
				validcon=true;
			else{
			//otherwise we determine for each direction whetehr we are going same way or not.
				int[] prevchip = (int[])played.front().next().item();
				//if last vertical this move must not be
				if(nextchip[0]== lastchip[0]){
					if(lastchip[0]!=prevchip[0])
						validcon=true;
				
				//else if last horizontal this move must not be
				}else if(nextchip[1]==lastchip[1])
				{
					if(lastchip[1]!=prevchip[1])
						validcon=true;
				//else northeast last horizontal this move must not be
				}else if(nextchip[0]>lastchip[0]&&nextchip[1]>lastchip[1]){
					if(lastchip[0]<=prevchip[0]||lastchip[1]<=prevchip[1])
						validcon=true;
						
				//else northwest last horizontal this move must not be
				}else if(nextchip[0]>lastchip[0]&&nextchip[1]<lastchip[1]){
					if(lastchip[0]<=prevchip[0]||lastchip[1]>=prevchip[1])
						validcon=true;
				//else southeast last horizontal this move must not be
				}else if(nextchip[0]<lastchip[0]&&nextchip[1]>lastchip[1]){
					if(lastchip[0]>=prevchip[0]||lastchip[1]<=prevchip[1])
						validcon=true;
						
				//else southwest last horizontal this move must not be
				}else if(nextchip[0]<lastchip[0]&&nextchip[1]<lastchip[1]){
					if(lastchip[0]>=prevchip[0]||lastchip[1]>=prevchip[1])
						validcon=true;
				}
			}


				//always rotate our last front nextchip test to the back of remaining chips by first removign from frotn
				//remaining.front().remove();
				
				//if we have a valid connection to continue on insert our removed front chip into the played chips list
				// we will then see if this chip when played leads to completion
				//after this test we remove it from played chips again.
				int complete=0;
				if(validcon){
				
				
					DList played2 = new DList();
					DListNode playedpos = played.front();
					int n;
					
					
					DList remaining2 = new DList();
					DListNode remainingpos = remaining.front();
					for(n=0;n<remaining.length();n++)
					{	if(n != N)
							remaining2.insertFront(remainingpos.item());
						else
							played2.insertFront(remainingpos.item());

						remainingpos=remainingpos.next();
					}
					for(n=0;n<played.length();n++)
					{
						played2.insertBack(playedpos.item());
						
						playedpos=playedpos.next();
					}
					completeness = testcompletion(played2,remaining2,depth-1,color,zone);
					complete = 10*completeness + played.length();
					
					if (completeness== 100000000){
						return 100000000;
					}
				}
				  //if we have a valid connection to continue on that we tested
			if(validcon){
											
				//if we foudn a network return immediately
				if (completeness== 100000000){
					return 100000000;
				}
				//otherwise if we have found a new maximum score set that as max.
				if(complete > max)
					max = complete;
			}
				
			
		}
	}
	
	}catch( Exception E){
	}

	return max;
   }
   
   
   // this is a test completion recursion wrapper
   // we test to see if each row0 goal chip of our color turns into a network.
   // if it does not we return a score for its max length
   //otherwise we return 100000 for the max score
   public int testcompletion(int color){
   	//DList played = new DList();
	DList played;
	DList remaining = new DList();
	int i,j,max=0,zone=0;
	int complete=0;
	// as no network can have 2 chips in row 0 we wont insert any chips in row zero

	for( i=1;i<8;i++)
		for(j=1;j<8;j++)
			if(board[i][j] == color)
				remaining.insertFront(new int[]{i,j});
	
	int[] next = new int[2];
	for(i=1;i<7;i++)
		if(board[i][0]==color || board[0][i]==color)
			zone++;

	for(i=1;i<7;i++){
		// for all chips of our color in row 0, we see if they begin a completed network.
		if(board[i][0]==color ){
			next[0]=i;
			next[1]=0;
			played = new DList(new int[][]{next});
			complete=10*testcompletion(played,remaining,100,color,zone-1)+zone;
			if(complete >= 100000000)
			{
				return 100000000;
				}
			if(complete>max)
				max=complete;
		}
		
		if(board[0][i]==color ){
			next[0]=0;
			next[1]=i;
			played = new DList(new int[][]{next});

			complete=10*testcompletion(played,remaining,100,color,zone-1)+zone;

			if(complete >= 100000000){
			
				return 100000000;
				}
			if(complete>max)
				max=complete;
		}
	}
	return max;
   }
	
	
	//this function uses our testcompletion function
	//if our testcompletion function reutnrs ints compltee value(100,000)
	// we know we found a network
public boolean hasNetwork(int playerColor){
int complete = testcompletion(playerColor);
	

	if(complete>= 100000000)
	{
		return true;
	}
	return false;
	}



    private int MachinePlayerColor(MachinePlayer mp) {
        return mp.myColor();
    }
	// this function undoes move m for the given color(used afte performing a move for evaluation)
    boolean undo(Move m, int color){
    	if(m.moveKind == Move.ADD){
		board[m.x1][m.y1]=-1;
		chipcount--;
	}
	if(m.moveKind == Move.STEP){
		board[m.x2][m.y2]=color;
		board[m.x1][m.y1]=-1;
	}
	return true;
    }
    
}
