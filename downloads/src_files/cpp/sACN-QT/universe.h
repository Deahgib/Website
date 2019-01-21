#ifndef UNIVERSE_H
#define UNIVERSE_H

class Universe
{
public:
    Universe(short number);
    ~Universe();

    void setDMXValAt(short index, char val);
    char getValAt(short index);
private:
    short universeNumber;
    char* dmxLevels;
};

#endif // UNIVERSE_H
