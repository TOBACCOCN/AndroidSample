package com.example.sample.mqtt;

import com.elvishew.xlog.XLog;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DefaultMqttCallBack implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        XLog.i( "CONNECTION_LOST");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe 后得到的消息会执行到这里面
        XLog.i("MESSAGE_ARRIVED, TOPIC: [%s], QOS: [%d], MESSAGE: [%s]", topic, message.getQos(), new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        XLog.i("DELIVERY_COMPLETE: [%s]", token.isComplete());
    }
}