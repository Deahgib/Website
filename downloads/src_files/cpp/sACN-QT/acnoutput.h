#ifndef ACNOUTPUT_H
#define ACNOUTPUT_H

#include <QObject>

class ACNOutput
{
public:
    static void initACN();
    static QByteArray getOutputByteArray();
    static char* getOutput();
    static void setValAt(short index, char val);
private:
    static char* output;
};

#endif // ACNOUTPUT_H
