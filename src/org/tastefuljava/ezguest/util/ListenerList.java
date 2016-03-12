package org.tastefuljava.ezguest.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListenerList implements InvocationHandler {
    private final List<Object> listeners = new ArrayList<>();

    public ListenerList() {
    }

    public void addListener(Object listener) {
        listeners.add(listener);
    }

    public void removeListener(Object listener) {
        listeners.remove(listener);
    }

    @SuppressWarnings("unchecked")
    public <T> T getNotifier(Class<T> intf) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return (T)Proxy.newProxyInstance(cl, new Class[] {intf}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Class listenerClass = method.getDeclaringClass();
        for (Iterator it = listeners.iterator(); it.hasNext(); ) {
            Object listener = it.next();
            if (listenerClass.isInstance(listener)) {
                method.invoke(listener, args);
            }
        }
        return null;
    }
}
