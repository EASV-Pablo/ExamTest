package Core.Interfaces;

import java.util.List;

public interface IRepository <T>{

    List<T> GetAll();
    T Get(int id);
    void Add(T entity);
    void Edit(T entity);
    void Remove(int id);

}
