package com.lukebroglio.BookAssociation;

import org.springframework.lang.NonNull;

import java.util.*;


/**
 * Stores data in an array like format with array style indexing. Items are stored in order of an assigned weight
 * given to each item.
 *
 * @author Luke Broglio
 * @param <T> The type of data this list stores
 */
public class WeightedList<T> implements Collection<T> {
    /**
     * Class to hold an entry in this list with its weight
     */
    public class ListEntry {
        /**
         * The data stored in this entry
         */
        public T data;
        /**
         * The weight of this entry
         */
        public int weight;

        /**
         * Creates a new ListEntry with the given data and a weight of 0
         *
         * @param data The data this entry contains
         */
        ListEntry(T data){
            this.data = data;
            this.weight = 0;
        }

        /**
         * Creates a new ListEntry with the given data and weight
         *
         * @param data The data for this ListEntry to contain
         * @param weight The weight of this entry
         */
        ListEntry(T data, int weight){
            this.data = data;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o){
            if(o.getClass().equals(ListEntry.class)){
                return data.equals(((ListEntry) o).data);
            }
            else {
                return data.equals(o);
            }
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }

        @Override
        public String toString(){
            return data.toString();
        }
    }

    private class WeightedListIterator implements Iterator<T>{
        /**
         * Stores the location of this iterator within the {@link WeightedList}
         */
        private int location = -1;
        @Override
        public boolean hasNext() {
            return (location + 1 < contents.size());
        }

        @Override
        public T next() {
            location++;
            return contents.get(location).data;
        }
    }

    /**
     * {@link ArrayList} storing the items that have been added to this WeightedList in {@link ListEntry}
     */
    ArrayList<ListEntry> contents;


    /**
     * Constructs a new WeightedList with a starting size of 10
     */
    public WeightedList(){
        contents = new ArrayList<ListEntry>();
    }

    /**
     * Constructs a new WeightedList with the given initial size
     *
     * @param startSize The size to initialize the backing {@link ArrayList} with
     */
    public WeightedList(int startSize){
        contents = new ArrayList<ListEntry>(startSize);
    }
    /**
     * Swaps the data at the two given indexes within the given array
     *
     * @param arr The array to operate in
     * @param i1 The index of the first piece of data in the given array
     * @param i2 The index of the second piece of data in the given array
     */
    private void swap(ArrayList<ListEntry> arr,int i1, int i2){
        //Save the entry at i1

        ListEntry temp = arr.get(i1);
        //Put the entry at i2 at i1
        arr.set(i1,arr.get(i2));

        //put the entry at i1 at i2
        arr.set(i2,temp);
    }

    /**
     * Partitions the given array around a pivot point as part of QuickSort
     *
     * @param arr The array to operate on
     * @param low The lower index of the section of the array to operate on
     * @param high The upper index of the section of the array to operate on
     *
     * @return The index of the pivot at the end of the process
     */
    private int partition(ArrayList<ListEntry> arr,int low, int high){
        int pivotWeight = arr.get(high).weight;

        int lowLoc = low -1;
        int highLoc;

        for(highLoc = low; highLoc < high; highLoc++){
            if(arr.get(highLoc).weight > pivotWeight){
                lowLoc++;
                swap(arr,lowLoc,highLoc);
            }
        }

        swap(arr, lowLoc + 1, high);
        return (lowLoc + 1);
    }

    /**
     * Performs the quicksort algorithm on the contents of this weighted list
     *
     * @param low The lower index of the section of the contents to sort
     * @param high The upper index of the section of the contents to sort
     */
    private void quickSort(int low, int high){
        if(low < high){
            int pivotLoc = partition(contents,low,high);

            quickSort(low,pivotLoc -1);
            quickSort(pivotLoc+1,high);
        }
    }

    /**
     * Sorts the contents of this array by their weight. (Largest Weight will be at index 0)
     */
    private void sortContents(){
        quickSort(0,contents.size() -1);
    }


    @Override
    public int size() {
        return contents.size();
    }

    @Override
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o){
      ListEntry checkEntry = new ListEntry((T)o);

      return contents.contains(checkEntry);
    }

    @Override
    public Iterator<T> iterator() {
        return new WeightedListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] asArray = new Object[contents.size()];

        for(int i = 0; i < contents.size(); i++){
            asArray[i] =  contents.get(i).data;
        }

        return asArray;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(@NonNull E[] a) {
        for(int i = 0; i < contents.size(); i++){
            a[i] =  (E) contents.get(i).data;
        }

        return a;
    }

    /**
     * Increases the weight of the given item by one
     *
     * @param t The item to increment the weight of
     *
     * @return true if the operation was successful; false if it fails (Fails are caused by the item not being in the list)
     */
    public boolean incrementItemWeight(T t){
        if(contents.contains(new ListEntry(t))){
            contents.get(contents.indexOf(new ListEntry(t))).weight += 1;
            return true;
        }
        return false;
    }

    /**
     * Adds the given element t with a weight of 0 if it isn't already in the list. If it is already in the list
     * increments its weight by one.
     * Sorts the contents of the List to maintain its order by weight after adding.
     *
     * @param t The element to add into the list
     *
     * @return true if the add was successful; false if it fails.
     */
    @Override
    public boolean add(T t) {
       ListEntry toAdd = new ListEntry(t,0);

        boolean flag;

       if(contents.contains(new ListEntry(t))){
           incrementItemWeight(t);
           flag = false;
       }
       else{
           flag = contents.add(toAdd);

       }

        sortContents();
        return flag;


    }

    /**
     * Adds the given element t with the given weight if it isn't already in the list. If it is already in the list
     * increments its weight by one.
     * Sorts the contents of the List to maintain its order by weight after adding.
     *
     * @param t The element to add into the list
     * @param weight The weight this element should be inserted into the list with.
     *
     * @return true if the add was successful; false if it fails.
     */
    public boolean addWithWeight(T t, int weight){
        ListEntry toAdd = new ListEntry(t,weight);
        boolean flag;

        if(contents.contains(new ListEntry(t))){
            incrementItemWeight(t);
            flag = false;
        }
        else{
            flag = contents.add(toAdd);

        }

        sortContents();
        return flag;

    }
    private boolean addNoSort(T t,int weight){
        ListEntry toAdd = new ListEntry(t,weight);

        if(contents.contains(new ListEntry(t))){
            incrementItemWeight(t);
            return false;
        }
        else{
            return contents.add(toAdd);
        }
    }
    @Override
    public boolean remove(Object o) {
        return contents.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return contents.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Iterator<? extends T> cIterator = c.iterator();
        boolean success = true;
        while(cIterator.hasNext()){

            if(!addNoSort(cIterator.next(), 0)){
                success = false;
            }

        }

        sortContents();

        return success;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return contents.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return contents.retainAll(c);
    }

    @Override
    public void clear() {
        contents.clear();
    }

    /**
     * Returns the data stored an index i in the list
     *
     * @param i The index to get the data from
     *
     * @return The data at i
     */
    public T get(int i){
        return contents.get(i).data;
    }

    /**
     * Returns the weight of the entry at the given index
     *
     * @param i The index to get the weight for
     *
     * @return The weight of the entry at index i
     */
    public int getWeight(int i){
        return contents.get(i).weight;
    }

    /**
     * Gets the index of the given element in the backing array
     *
     * @param element The element to get the index of
     *
     * @return The index of the item in the backing array
     */
    public int indexOf(T element){
        return contents.indexOf(new ListEntry(element));
    }

    /**
     * Sets the weight of the ListEntry with the given data to the given weight
     *
     * @param data The data to set the weight of
     * @param newWeight The new weight to assign to the given data
     *
     * @return True if the change in weight was successful false if it wasn't (EX/ The given data isn't in the list)
     */
    public boolean setWeight(T data, Integer newWeight) {
        int indexToSet = this.indexOf(data);

        if(indexToSet != -1){
            contents.get(indexToSet).weight = newWeight;
            return true;
        }
        return false;

    }

}
