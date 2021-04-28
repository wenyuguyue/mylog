# mylog
使用elk进行链路日志记录
kafka配置项host.name advertised.host.name
遇到的问题：

在本机或者其他机器telnet IP 9092，通，使用域名也通，telnet 127.0.0.1 9092不通

 

 

host.name：按配置文件说明，是Kafka绑定的interface。其实这个说明有点误导，下面会见到。

advertised.host.name：是注册到zookeeper，client要访问的broker地址。（可能producer也是拿这个值，没有验证）

如果advertised.host.name没有设，会用host.name的值注册到zookeeper，如果host.name也没有设，则会使用JVM拿到的本机hostname注册到zk。

这里有两个坑要注意：

如果advertised.host.name没有设，host.name不能设为0.0.0.0，否则client通过zk拿到的broker地址就是0.0.0.0。
如果指定要bind到所有interface，host.name不设就可以。

如果host.name和advertised.host.name都不设，client通过zk拿到的就是JVM返回的本机hostname，如果这个hostname是client无法访问到的，client就会连不上broker。
所以如果要bind到所有interface，client又能访问，解决的办法是host.name不设或设置0.0.0.0，advertised.host.name一定要设置为一个client可以访问的地址，如直接设IP地址。
如果不需要bind到所有interface，也可以只在host.name设置IP地址。

简单的检查broker是否可以被client访问到的办法，就是在zookeeper中看broker信息，上面显示的hostname是否是client可以访问到的地址。
在zkCli中执行get /brokers/<id>

 

 

advertised.listeners=PLAINTEXT://docp1:9092 设了这个值就不用advertised.host.name 和 host.name选项了 
