/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This interface defines the Observer in the Observer design pattern. It provides a method to update the observer based on changes in the subject.
*/
package Observer_design_pattern;

public interface Observer {
    void update(String eventType, Object data);
}
