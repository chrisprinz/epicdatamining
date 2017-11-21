import pylab
import numpy

def calculate_s_curve(r,b, s):
    return 1-((1-s**r)**b)

x = numpy.linspace(0,1,10000)
y_1 = calculate_s_curve(3, 10, x)
y_2 = calculate_s_curve(6, 20, x)
y_3 = calculate_s_curve(5, 50, x)

pylab.plot(x,y_1)
pylab.title('r=3 and b=10')
pylab.savefig("plot_1.png")
pylab.clf()

pylab.plot(x,y_2, '-r')
pylab.title('r=6 and b=20')
pylab.savefig("plot_2.png")
pylab.clf()

pylab.plot(x,y_3, '-g')
pylab.title('r=5 and b=50')
pylab.savefig("plot_3.png")