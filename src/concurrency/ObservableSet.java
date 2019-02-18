package concurrency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 观察者模式
 * @author renzhijiang
 *
 */
public class ObservableSet<E> {
    
    private final List<SetObserver<E>> list = new ArrayList<>();
    
    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (list) {
            return list.remove(observer);
        }
    }
    public void addObserver(SetObserver<E> observer) {
        synchronized (list) {
            list.add(observer);
        }
    }
    
    private void notifyElement(E e) {
        synchronized (list) {
            for (SetObserver<E> observer : list) {
                // 同步区域不应该调用外部的方法, 极有可能造成问题
                // 同步区域应该 简洁, 简短
                observer.update(this, e);
            }
        }
    }
    public boolean add(E e) {
        if (set.add(e) == true) {
            notifyElement(e);
            return true;
        }
        return false;
    }
    
    private Set<E> set = new HashSet<>();
    public static void main(String[] args) {
        ObservableSet<Integer> observerSet = new ObservableSet<>();
        observerSet.addObserver(new SetObserver<Integer>() {

            @Override
            public void update(ObservableSet<Integer> set, Integer element) {
//                if (element.intValue() == 23) 
//                    set.removeObserver(this); // 这里会抛出 concurrentModificationException
                  System.out.println(element);
            }
        });
        for (int i = 0;i < 100;i++) {
            observerSet.add(i);
        }
  
    }
    
}
interface SetObserver<E>{
    void update(ObservableSet<E> set, E element);

}
