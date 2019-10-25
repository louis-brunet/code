target : chess

OFILES = board.o\
		 cell.o\
		 piece.o\
		 main.o

CC = gcc

CFLAGS = -Wall -ansi -pedantic -g

board.o : board.h cell.h

cell.o : cell.h

piece.o : piece.h

main.o : board.h cell.h piece.h

chess : $(OFILES)
	$(CC) $(CFLAGS) -o chess $(OFILES)

clean : rm -f $(OFILES) chess