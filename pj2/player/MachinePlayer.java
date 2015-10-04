package player; 
  
import java.util.Random; 
  
import list.InvalidNodeException; 
import list.*; 
  
/** 
 * An implementation of an automatic Network player. Keeps track of moves made 
 * by both players. Can select a move for itself. 
 */
public class MachinePlayer extends Player { 
    java.util.Random rnd; 
    int mycolor; 
    int depth; 
    Board b; 
  
    // int chipcount; 
    // Creates a machine player with the given color. Color is either 0 (black) 
    // or 1 (white). (White has the first move.) 
    public MachinePlayer(int color) { 
        b = new Board(color); 
  
        depth = 3; 
        rnd = new Random(); 
        mycolor = color; 
  
    } 
  
    // Creates a machine player with the given color and search depth. Color is 
    // either 0 (black) or 1 (white). (White has the first move.) 
    public MachinePlayer(int color, int searchDepth) { 
        b = new Board(color); 
  
        rnd = new Random(); 
  
        mycolor = color; 
  
        depth = searchDepth; 
    } 
  
    // Returns a new move by "this" player. Internally records the move (updates 
    // the internal game board) as a move by "this" player. 
    @Override
    public Move chooseMove(){
	Best myBest;
        myBest = alphaBeta(depth, -1000000, 1000000, mycolor); 
	
  	b.move(myBest.move,mycolor);
        return myBest.move; 
        
    } 
    
    
       
       //this was my first non-alpha-beta way to choose moves.. I inserted alpha-beta testign at the beginning  to make it prefer that if that works.
       //outside of findign moves leading to a netowrk I evaluate each player based on the number of connections.
    public Move chooseMove2() {
  Best myBest;
        myBest = alphaBeta(depth, -1000000, 1000000, mycolor); 
	if(myBest.score == 100000)
	{
	b.move(myBest.move,mycolor);
        return myBest.move; 
	}
  	//retreive a list of valid moves
      DList L = b.validMoves(mycolor);
      int lsize = L.length();
      Move[] moves= new Move[lsize];
      int movepos = 0;
      try{
      while ( movepos<lsize){
      int nextpos = rnd.nextInt(lsize);
      	DListNode dln = L.item(nextpos);
	moves[movepos]=(Move)dln.item();
	dln.remove();
	movepos++;
	lsize--;
      }

            L = new DList(moves);

      }catch(Exception E){}
      //set our list iterator to the beginning of the valid moves list
      DListNode ln = L.back();//*
      Move m = new Move();
      int max = -100000000;//set ourmaximum to the lowest value possible
      try{
	m= (Move)ln.item();
      }catch(Exception E){}
      for(int M=0;M<L.length();M++){
	try{
		Move m2= (Move)ln.item();
	
		b.move(m2,mycolor);//try the current move
		int cur = b.evaluate(mycolor);//save the current score of the move being evaluated
		b.undo(m2,mycolor);

		if(cur >max){//if we found a new best move take it
			max = cur;
			m=m2;
		}
		
		ln=ln.prev();//advance to next posible move
	}catch(Exception E){
	}
      }
      b.move(m,mycolor);//perform the best move

      return m;
  } 
  
  
    // If the Move m is legal, records the move as a move by the opponent 
    // (updates the internal game board) and returns true. If the move is 
    // illegal, returns false without modifying the internal state of "this" 
    // player. This method allows your opponents to inform you of their moves. 
    @Override
    public boolean opponentMove(Move m) { 
  
        int oponentcolor = 1 - mycolor; 
        if (!b.validMove(m, oponentcolor)) { 
            return false; 
        } 
        b.move(m, oponentcolor); 
        return true; 
    } 
  
    // If the Move m is legal, records the move as a move by "this" player 
    // (updates the internal game board) and returns true. If the move is 
    // illegal, returns false without modifying the internal state of "this" 
    // player. This method is used to help set up "Network problems" for your 
    // player to solve. 
    @Override
    public boolean forceMove(Move m) { 
        if (b.validMove(m, mycolor)) { 
            return true; 
        } 
        return false; 
    } 
  
    // Returns the Best possible move up to a certain search depth 
    // using alpha-beta pruning to speed up the process 
  
    public Best alphaBeta(int mydepth, int alpha, int beta, int player) { 
        Best myBest = new Best(); 
        Best reply; 
  	DList L = b.validMoves(player); 
	try{
	myBest.move = (Move)L.front().item();
	}catch(Exception E){}
        if (b.hasNetwork(mycolor)) { 
            myBest.score = 100000000; 
            return myBest; 
        } 
	
        if (b.hasNetwork(1-mycolor)) { 
            myBest.score = -100000000; 
            return myBest; 
        } 
        if (mydepth ==0) { 
            reply = new Best(); 
            int score = b.evaluate(mycolor); // set it to evaluate(); 
            reply.score = score; 
            return reply; 
        } 

        if (player == mycolor) { 
            myBest.score = alpha; 
        } else { 
            myBest.score = beta; 
        } 
	try{
	int listlen = L.length();
	int pos=listlen-1;
        myBest.move = (Move)L.back().item(); 
	reply = myBest;
        ListNode current = L.back(); 
        while (pos>=0) { 
	    Move mymove = (Move)current.item();

                b.move(mymove, player);
                reply = alphaBeta( mydepth - 1, alpha, 
                        beta, 1-player); 
           
                b.undo(mymove,player); 
  
                if (player == mycolor    && reply.score > myBest.score ) { 
                    myBest.move = mymove; 
                    myBest.score = reply.score; 
		    if(myBest.score >=100000000)
		    	return myBest;
                }
		if (player == 1 - mycolor && reply.score < myBest.score) { 
                    myBest.move = mymove; 
                    myBest.score = reply.score; 
		    
		    if(myBest.score <=-100000000)
		    	return myBest;
                } 
		current = current.prev(); 
		pos--;
              
        } 
	}catch(Exception e){
	}
        return myBest; 
    } 
  
    public int myColor() { 
        return mycolor; 
    } 
  
} 
