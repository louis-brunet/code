goal : checkers

OFILES = piece.o \
	board.o \
	movement.o \
	main.o

CC = gcc

CFLAGS = -Wall -ansi -g -pedantic  

piece.o : piece.h

board.o : board.h piece.h

movement.o : movement.h board.h piece.h

main.o : board.h piece.h movement.h

checkers : $(OFILES)
	$(CC) $(CFLAGS) -o checkers $(OFILES)

#clean : rm -f $(OFILES) checkers