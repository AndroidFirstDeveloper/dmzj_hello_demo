// BookController.aidl
package com.example.server;

// Declare any non-default types here with import statements
import com.example.server.Book;
interface BookController {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    List<Book> getBookList();
    void addBookInOut(inout Book book);
    void addBookIn(in Book book);//In 类型的表现形式是：数据只能由客户端传向服务端，服务端对数据的修改不会影响到客户端
    void addBookOut(out Book book);//Out类型的表现形式是：数据只能由服务端传向客户端，即使客户端向方法接口传入了一个对象，该对象中的属性值也是为空的，即不包含任何数据，服务端获取到该对象后，对该对象的任何操作，就会同步到客户端这边
}
