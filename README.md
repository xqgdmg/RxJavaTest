# RxJavaTest
与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。</br>
除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。</br>
