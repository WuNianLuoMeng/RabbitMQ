package topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import simple.MyConsumer;

/**
 * 自定义消费者类型
 */
public class TopicsConsumer1 {
    public static void main(String[] args) throws Exception {
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

        String queueName = "topics_queue_1";

        channel.queueDeclare(queueName, false, false, false, null);
        /**
         * 参数列表
         * 1，监听的消息队列的名称
         * 2，消息队列的应答模式
         * 3，读取消息处理对象
         */
        channel.basicConsume(queueName, true, new MyConsumer(channel));

    }
}