/* HashTableChained.java */

package dict;
import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/

List[] lists;
int numentries;
int items;
int a=2,b=4,p=7919;
  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    lists = new List[sizeEstimate];
    numentries=sizeEstimate;
    items=0;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    numentries=101;
    lists = new List[numentries];
        items=0;

  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
      return Math.abs((a*code+b)%p)%numentries;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return items;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    for(int N=0;N<numentries;N++)
        if(!lists[N].isEmpty())
            return false;
    return true;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    if ( key == null || value == null)
        return null;
    Entry e = new Entry();
    e.key=key;
    e.value=value;
    
    int entry = compFunction(key.hashCode());
    //    System.out.println("value: "+value + "\tkey: "+key+"\tentry: "+entry);

    if (lists[entry]== null)
        lists[entry] = new DList();
    lists[entry].insertBack(e);
    items++;
    return e;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
  	try{
    int entry = compFunction(key.hashCode());
   
    ListNode ln = lists[entry].front();
    while ( ln !=lists[entry].back()){
    	if( ((Entry)ln.item()).key == key){
		//System.out.println("found key: " + key);
		return (Entry)ln.item();
	}
	ln=ln.next();
    }
    
    	if( ((Entry)ln.item()).key == key){
		//System.out.println("found key: " + key);
		return (Entry)ln.item();
	}
	}catch(Exception E){
		//System.out.println("we have an exception");
	}

    // Replace the following line with your solution.
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    int entry = compFunction(key.hashCode());
    System.out.println("hash "+entry);
    ListNode ln = lists[entry].front();
        try{

    while ( ln !=lists[entry].back()){
    	if( ((Entry)ln.item()).key == key){
		Entry e = (Entry)ln.item();
		ln.remove();
		items--;
		return (Entry)ln.item();
	}
	ln=ln.next();
    }

	}catch(Exception E){
		System.out.println("we have an exception");
	}
    // Replace the following line with your solution.
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
  for(int N=0;N<numentries;N++)
  	if(lists[N] != null)
  	while ( !lists[N].isEmpty()){
		  try{
			 lists[N].back().remove();
		  }catch(Exception E){
			   System.out.println("Exception empty");
		  }
	   }
    // Your solution here.
  }

  public static void main(String[] argv){
    HashTableChained table = new HashTableChained(); 
    table.insert("A", "I have an A"); 
    table.insert("B", "I have an B");
    table.insert("C", "I like Hilary Lee"); 
    table.insert(2, 10); 

    Entry a = table.find("A"); 
    Entry b = table.find("B"); 
    table.remove("C"); 
    Entry c = table.find("C"); 
    System.out.println("Should be 4: " + table.size()+b.value()); 
  }

}
