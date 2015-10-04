package list; 

public class LockDListNode extends DListNode {
	protected boolean locked = false; 

	LockDListNode(Object i, DListNode p, DListNode n){
		super(i, p, n);
	}

}