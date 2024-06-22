/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This interface defines the Subject in the Observer design pattern. It provides methods to add, remove, and notify observers.
*/
package Observer_design_pattern;
import java.util.List;
import java.util.ArrayList;
public interface Subject{
    List<Observer> observers = new ArrayList<>();

    default void addObserver(Observer observer) {
        observers.add(observer);
    }

    default void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    default void notifyObservers(String eventType, Object data) {
        for (Observer observer : observers) {
            observer.update(eventType, data);
        }
    }
}
