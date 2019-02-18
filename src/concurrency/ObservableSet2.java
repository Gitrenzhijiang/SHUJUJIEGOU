package concurrency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 观察者模式
 * @author renzhijiang
 *
 */
public class ObservableSet2<E> {
    
    private final List<SetObserver2<E>> list = new CopyOnWriteArrayList<>();
    
    public boolean removeObserver(SetObserver2<E> observer) {
        return list.remove(observer);
    }
    public void addObserver(SetObserver2<E> observer) {
        list.add(observer);
    }
    
    private void notifyElement(E e) {
        for (SetObserver2<E> observer : list) {
            observer.update(this, e);
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
        ObservableSet2<Integer> observerSet = new ObservableSet2<>();
        observerSet.addObserver(new SetObserver2<Integer>() {

            @Override
            public void update(ObservableSet2<Integer> set, Integer element) {
                if (element.intValue() == 23)
                    set.removeObserver(this); // 由于使用了copy on write 数据结构,
                  System.out.println(element);
            }
        });
        for (int i = 0;i < 100;i++) {
            observerSet.add(i);
        }
  
    }
    
}
interface SetObserver2<E>{
    void update(ObservableSet2<E> set, E element);

}
