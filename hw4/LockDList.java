package list; 

public class LockDList extends DList{

	protected DListNode newNode(Object i, DListNode p, DListNode n){
		return new LockDListNode(i, p, n);
	}

	public void lockNode(DListNode node){
		((LockDListNode) node).locked = true;
	}

	private boolean oflockNode(DListNode node){
		return ((LockDListNode) node).locked == true;
	}

	public void remove(DListNode node){
		if(oflockNode(node)){
			return;
		}else{
			super.remove(node);
		}
	}
}