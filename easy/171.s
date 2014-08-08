.section .data

bitmap:
.long                   0x3e, 0x7f, 0xfc, 0xf8, 0xf8, 0xfc, 0x7f, 0x3e

x:
.ascii                  "x"
space:
.ascii                  " "
linefeed:
.ascii                  "\n"

.section .text
.globl _start

_start:
movl                    $0,     %edi                # set %edi to 0

loop_start:
movl                    $8,     %esi                # (re)set %esi to 8
movl                    bitmap(,%edi,4),    %eax    # load word in %eax
cmpl                    $0xff,  %eax                # %eax > 0xff?
jg                      loop_exit                   # if so: jump to loop_exit
incl                    %edi                        # increase %edi
shift_start:
subl                    $0x1,   %esi                # decrease shift loop count
movl                    %eax,   %ebx                # copy %eax to %ebx
movl                    %esi,   %ecx
shrl                    %cl,    %ebx                # shift %cl places to the right
andl                    $0x1,   %ebx                # AND 1 onto %ebx
                                                    # if ebx 1, print "x", else print " "
pushl                   %eax                        # save %eax on stack
cmpl                    $0x1,   %ebx                # is %ebx 1?
je                      call_print_x                # if yes: print x
pushl                   $space                      # if not: print space
call                    print_char                  # call print_char
addl                    $0x4,   %esp                # remove $sapce from stack
jmp                     call_end                    # jump call_end
call_print_x:
pushl                   $x                          # push $x as arg on stack
call                    print_char                  # call print_char
addl                    $0x4,   %esp                # remove $x from stack
call_end:
popl                    %eax                        # pushed saved val back in %eax
cmpl                    $0x0,   %esi                # did we check all bits yet?
jg                      shift_start                 # if not, check next bit
pushl                   %eax                        # save eax onto stack
pushl                   $linefeed                   # push $linefeed as arg on stack
call                    print_char                  # call print_char
addl                    $0x4,   %esp                # remove $linefeed from stack
popl                    %eax                        # pop saved %eax back into register
jmp                     loop_start                  # jump to start of loop
loop_exit:
movl                    $0,     %ebx                # return code in %ebx
movl                    $1,     %eax                # exit syscall
int                     $0x80                       # kernel interrupt

.type print_char, @function
print_char:
pushl                   %ebp
movl                    %esp,   %ebp
movl                    $1,     %edx                # length of string to edx
movl                    $1,     %ebx                # FD (1 = stdout)
movl                    8(%ebp),%ecx                # get character from stack
movl                    $4,     %eax                # 4 = write systemcall
int                     $0x80                       # kernel interrupt
movl                    %ebp,   %esp
popl                    %ebp
ret
