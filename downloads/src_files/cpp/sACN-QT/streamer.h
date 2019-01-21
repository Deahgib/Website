#ifndef STREAMER_H
#define STREAMER_H

#include <QObject>
#include <QtNetwork/QUdpSocket>

class Streamer : public QObject
{
public:
    Streamer();
    ~Streamer();

    void sendDatagram();

private:
    QUdpSocket *socket;
    QHostAddress groupAddress;
};

#endif // STREAMER_H
