package one.nio.serial;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.util.Date;

class DateSerializer extends Serializer<Date> {

    DateSerializer() {
        super(Date.class);
    }

    @Override
    public void calcSize(Date obj, CalcSizeStream css) {
        css.count += 8;
    }

    @Override
    public void write(Date obj, ObjectOutput out) throws IOException {
        out.writeLong(obj.getTime());
    }

    @Override
    public Date read(ObjectInput in) throws IOException {
        return new Date(in.readLong());
    }

    @Override
    public void skip(ObjectInput in) throws IOException {
        in.skipBytes(8);
    }

    @Override
    public void toJson(Date obj, StringBuilder builder) {
        builder.append(obj.getTime());
    }
}
