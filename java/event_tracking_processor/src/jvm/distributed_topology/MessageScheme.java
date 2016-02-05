package distributed_topology;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by jinhong on 29/01/2016.
 */
public class MessageScheme implements Scheme {
    public List<Object> deserialize(byte[] ser) {
        try {
            String msg = new String(ser, "UTF-8");
            return new Values(msg);
        }catch (UnsupportedEncodingException e) {

        }
        return null;
    }
    public Fields getOutputFields() {
        return new Fields("msg");
    }
}
