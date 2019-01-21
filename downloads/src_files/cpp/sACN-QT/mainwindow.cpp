#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <iostream>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    universe = new Universe(1);
    streamer = new Streamer();


    timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()), this, SLOT(pingDMX()));
    timer->start(1000);
}

MainWindow::~MainWindow()
{
    delete ui;
    delete streamer;
    delete universe;
    delete timer;
}

void MainWindow::pingDMX()
{
    streamer->sendDatagram();
}

void MainWindow::faderSliderMoved()
{
    char level = (char)ui->levelSpinBox->value();
    char r = (char)ui->rSpinBox->value();
    char g = (char)ui->gSpinBox->value();
    char b = (char)ui->bSpinBox->value();
    char strobe = (char)ui->strobeSpinBox->value();
    universe->setDMXValAt(0,level);
    universe->setDMXValAt(1,r);
    universe->setDMXValAt(2,g);
    universe->setDMXValAt(3,b);
    universe->setDMXValAt(4,strobe);

    universe->setDMXValAt(0,0xFF);
    universe->setDMXValAt(1,0xc0);
    universe->setDMXValAt(2,0xff);
    universe->setDMXValAt(3,0xee);
    universe->setDMXValAt(4,0x00);
    streamer->sendDatagram();
}
