package work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作模式的生产者
 */
public class WorkProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置RabbitMQ服务主机地址，默认为localhost
        connectionFactory.setHost("localhost");
        // 设置RabbitMQ服务器端口
        connectionFactory.setPort(5672);
        // 设置虚拟主机名字，默认为/
        connectionFactory.setVirtualHost("/");
        // 设置用户，默认为guest
        connectionFactory.setUsername("guest");
        // 设置连接密码，默认为guest
        connectionFactory.setPassword("guest");
        // 创建连接
        Connection connection = connectionFactory.newConnection();
        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        /**
         * 参数列表含义
         * 1，消息队列的名称
         * 2，消息是否持久化
         * 3，当前消息队列是否被当前连接对象独占
         * 4，在消息使用完之后，是否删除消息
         * 5，附加参数
         */
        channel.queueDeclare("work_queue",true, false, false, null);
        // 创建消息
        String message = "Hello World";
        // 消息发送
        /**
         * 参数列表
         * 1，消息要发送的交换机对象，不写默认使用Default Exchange
         * 2，当前消息路由地址，简单消息模式，路由地址可以直接写成队列地址
         * 3，附加数据，可以不写
         * 4，所要发送的消息
         */
        channel.basicPublish("", "work_queue", null, message.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

}
