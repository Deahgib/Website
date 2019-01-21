#include "mainwindow.h"
#include <QApplication>
#include "streamer.h"

#include "acnoutput.h"
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;

    ACNOutput::initACN();

    Streamer *streamer = new Streamer();
    streamer->sendDatagram();
    delete streamer;




    w.show();



    return a.exec();
}
