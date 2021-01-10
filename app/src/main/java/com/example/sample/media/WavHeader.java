package com.example.sample.media;

public class WavHeader {

    private final byte[] header = new byte[44];

    public WavHeader(int fileLength, short channels, int sampleRate, int avgBytesPerSec,
                     short blockAlign, short bitsPerSample, int dataLength) {
        // 4byte，资源交换文件标识
        char[] fileId = {'R', 'I', 'F', 'F'};
        header[0] = (byte) fileId[0];
        header[1] = (byte) fileId[1];
        header[2] = (byte) fileId[2];
        header[3] = (byte) fileId[3];

        // 4byte，从下个地址开始到文件尾的总字节数
        header[4] = (byte) (fileLength & 0xff);
        header[5] = (byte) (fileLength >> 8 & 0xff);
        header[6] = (byte) (fileLength >> 16 & 0xff);
        header[7] = (byte) (fileLength >> 24 & 0xff);

        // 4byte，WAV 文件标识
        char[] wavId = {'W', 'A', 'V', 'E'};
        header[8] = (byte) wavId[0];
        header[9] = (byte) wavId[1];
        header[10] = (byte) wavId[2];
        header[11] = (byte) wavId[3];

        // 4byte，波形格式标识（fmt）, 最后一位空格
        char[] fmtId = {'f', 'm', 't', ' '};
        header[12] = (byte) fmtId[0];
        header[13] = (byte) fmtId[1];
        header[14] = (byte) fmtId[2];
        header[15] = (byte) fmtId[3];

        // 4byte，PCMWAVEFORMAT 的长度
        int fmtLength = 16;
        header[16] = (byte) (fmtLength & 0xff);
        header[17] = (byte) (fmtLength >> 8 & 0xff);
        header[18] = (byte) (fmtLength >> 16 & 0xff);
        header[19] = (byte) (fmtLength >> 24 & 0xff);

        // 2byte，格式种类（值为 1 时, 表示数据为线性 PCM 编码）
        short format = 1;
        header[20] = (byte) (format & 0xff);
        header[21] = (byte) (format >> 8 & 0xff);

        // 2byte，通道数, 单声道为 1, 双声道为 2
        header[22] = (byte) (channels & 0xff);
        header[23] = (byte) (channels >> 8 & 0xff);

        // 4byte，采样率, 比如 8000，16000，44100
        header[24] = (byte) (sampleRate & 0xff);
        header[25] = (byte) (sampleRate >> 8 & 0xff);
        header[26] = (byte) (sampleRate >> 16 & 0xff);
        header[27] = (byte) (sampleRate >> 24 & 0xff);

        // 4byte，波形数据传输速率（每秒平均字节数）， 大小为：通道数 * 采样率 * 采样位数 / 8
        header[28] = (byte) (avgBytesPerSec & 0xff);
        header[29] = (byte) (avgBytesPerSec >> 8 & 0xff);
        header[30] = (byte) (avgBytesPerSec >> 16 & 0xff);
        header[31] = (byte) (avgBytesPerSec >> 24 & 0xff);

        // 2byte，DATA 数据块长度，大小为通道数 * 采样字节数
        header[32] = (byte) (blockAlign & 0xff);
        header[33] = (byte) (blockAlign >> 8 & 0xff);

        // 2byte，采样位数, 即 PCM 位宽，通常为 8 位或 16位
        header[34] = (byte) (bitsPerSample & 0xff);
        header[35] = (byte) (bitsPerSample >> 8 & 0xff);

        // 4byte，数据标记符
        char[] dataId = {'d', 'a', 't', 'a'};
        header[36] = (byte) dataId[0];
        header[37] = (byte) dataId[1];
        header[38] = (byte) dataId[2];
        header[39] = (byte) dataId[3];

        // 4byte，接下来声音数据的总大小
        header[40] = (byte) (dataLength & 0xff);
        header[41] = (byte) (dataLength >> 8 & 0xff);
        header[42] = (byte) (dataLength >> 16 & 0xff);
        header[43] = (byte) (dataLength >> 24 & 0xff);
    }

    public byte[] getBytes() {
        return header;
    }

}
