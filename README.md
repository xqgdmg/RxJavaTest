# RxJavaTest
详情可以参阅：http://gank.io/post/560e15be2dca930e00da1083</br>

与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。</br>
除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。</br>
Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。 RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：</br>

Observable observable = Observable.create(new Observable.OnSubscribe<String>() {</br>
    @Override</br>
    public void call(Subscriber<? super String> subscriber) {</br>
        subscriber.onNext("Hello");</br>
        subscriber.onNext("Hi");</br>
        subscriber.onNext("Aloha");</br>
        subscriber.onCompleted();</br>
    }</br>
});</br></br>
just(T...): 将传入的参数依次发送出来。</br>
Observable observable = Observable.just("Hello", "Hi", "Aloha");</br>
// 将会依次调用：</br>
// onNext("Hello");</br>
// onNext("Hi");</br>
// onNext("Aloha");</br>
// onCompleted();</br></br>
from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。</br>
String[] words = {"Hello", "Hi", "Aloha"};</br>
Observable observable = Observable.from(words);</br>
// 将会依次调用：</br>
// onNext("Hello");</br>
// onNext("Hi");</br>
// onNext("Aloha");</br>
// onCompleted();</br></br>

订阅的方式很奇怪：</br>
observable.subscribe(observer);</br>
// 或者：</br>
observable.subscribe(subscriber);</br>

Action0 和 Action1</br>
Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的；</br>
Action1 也是一个接口，它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；</br>

# flatMap() 和 map()
map() 是一对一的转化，flatMap()是一对多的转化。</br>
  
和Retrofit结合，当请求成功后开线程需要处理数据的时候。</br>
doOnNext(Action1<? super T> onNext)</br>
Modifies the source Observable so that it invokes an action when it calls onNext.</br>
doOnNext does not operate by default on a particular Scheduler.</br>


