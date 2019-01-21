#include "streamer.h"
#include "string.h"

#include "acnoutput.h"

Streamer::Streamer():QObject()
{
    socket = new QUdpSocket(this);
    groupAddress = QHostAddress("239.255.0.1");
}

Streamer::~Streamer(){
    delete socket;
}

void Streamer::sendDatagram()
{
    QByteArray output = ACNOutput::getOutputByteArray();
    socket->writeDatagram(output.data(), output.size(), groupAddress, 5568);
}
