package cn.mrpei.search.message;



import cn.mrpei.common.exception.MallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import java.util.List;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/22
 * @last-modified ：
 * @class cn.mrpei.search.message
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class MyMessageListener implements MessageListener {

    private final static Logger log= LoggerFactory.getLogger(MyMessageListener.class);

    @Override
    public void onMessage(Message message) {
        //取消息内容
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            log.info(text);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new MallException("监听消息失败");
        }
    }
}
