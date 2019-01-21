#include "universe.h"
#include "acnoutput.h"

Universe::Universe(short number)
{
    universeNumber = number;
    dmxLevels = new char[512];
    for(int i = 0; i < sizeof(dmxLevels); i++){
        dmxLevels[i] = 0x00;
    }
}

Universe::~Universe()
{
    delete dmxLevels;
}


void Universe::setDMXValAt(short index, char val){
    dmxLevels[index] = val;
    ACNOutput::setValAt(short(index + 126), val);
}

char Universe::getValAt(short index){
    return dmxLevels[index];
}
