package com.crazymakercircle.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static com.crazymakercircle.netty.bytebuf.PrintAttribute.print;


public class CompositeBufferTest {

    @Test
    public void intCompositeBufComposite() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer(3);
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer(new byte[]{1, 2, 3}));
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer(new byte[]{4}));
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer(new byte[]{5, 6}));

        print("动作：addComponent ", compositeByteBuf);
        showMsg(compositeByteBuf);

        iterateMsg(compositeByteBuf);

        //合并成一个单独的缓冲区
        ByteBuffer nioBuffer = compositeByteBuf.nioBuffer(0, 6);


        byte[] bytes = nioBuffer.array();
        System.out.print("bytes = ");
        for (byte b : bytes) {
            System.out.print(b);
        }
        compositeByteBuf.release();
    }

    @Test
    public void byteBufComposite() {
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        //消息头
        ByteBuf headerBuf = Unpooled.wrappedBuffer(utf8("疯狂创客圈:"));
        //消息体1
        ByteBuf bodyBuf = Unpooled.wrappedBuffer(utf8("高性能 Netty"));
        compositeByteBuf.addComponents(headerBuf, bodyBuf);
        print("动作：addComponent 1", compositeByteBuf);
        showMsg(compositeByteBuf);

        iterateMsg(compositeByteBuf);

        headerBuf.retain();
        compositeByteBuf.release();

        compositeByteBuf = Unpooled.compositeBuffer(2);

        //消息体2
        bodyBuf = Unpooled.wrappedBuffer(utf8("高性能学习社群"));
        compositeByteBuf.addComponents(headerBuf, bodyBuf);


        print("动作：addComponent 2", compositeByteBuf);

        showMsg(compositeByteBuf);

        iterateMsg(compositeByteBuf);

        compositeByteBuf.release();
    }

    static Charset utf8Code = Charset.forName("UTF-8");

    private byte[] utf8(String s) {
        return s.getBytes(utf8Code);
    }

    private void showMsg(CompositeByteBuf b) {
        System.out.println(" showMsg ..........");
                //处理整个消息
        int length = b.readableBytes();
        byte[] array = new byte[length];
        //将CompositeByteBuf中的数据复制到数组中
        b.getBytes(b.readerIndex(), array);
        //处理一下数组中的数据
        System.out.println(" content： " + new String(array, utf8Code));


    }

    private void iterateMsg(CompositeByteBuf cbuf) {
        System.out.println(" iterateMsg .......... ");
        //处理整个消息
        for (ByteBuf b : cbuf) {
            int length = b.readableBytes();
            byte[] array = new byte[length];
            //将CompositeByteBuf中的数据复制到数组中
            b.getBytes(b.readerIndex(), array);
            //处理一下数组中的数据
            System.out.print(new String(array, utf8Code));
        }
        System.out.println();
    }

}