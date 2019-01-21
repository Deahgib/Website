#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "streamer.h"
#include "universe.h"

#include <QTimer>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();


private slots:
    void pingDMX();
    void faderSliderMoved();

private:
    Universe *universe;
    Streamer *streamer;
    QTimer *timer;

    Ui::MainWindow *ui;

};

#endif // MAINWINDOW_H
