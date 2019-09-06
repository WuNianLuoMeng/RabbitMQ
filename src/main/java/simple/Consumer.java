package simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 简单消息模式-消费者
 */
public class Consumer {
    private static String s = null;

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
        // 创建消费者，并进行消息监听
        // 第一步，创建消息处理函数，默认的消息处理函数
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收到的消息为：" + new String(body, StandardCharsets.UTF_8));
            }
        };
        // 第二步，进行监听
        /**
         * 参数列表
         * 1，监听的消息队列的名称
         * 2，消息队列的应答模式
         * 3，读取消息处理对象
         */
        channel.basicConsume("simple_queue", true, defaultConsumer);

        // 关闭资源
        channel.close();
        connection.close();
    }
}
