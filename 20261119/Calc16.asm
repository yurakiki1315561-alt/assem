; ============================================================
; Calc16.asm
; 32-bit MASM / Visual Studio / Irvine32
;
; - Reads two signed 16-bit integers (A, B)
; - Performs + - * / (quotient and remainder)
; - After EACH operation prints the real contents of AX,BX,CX,DX
;
; Register convention used in every operation:
;   AX = result (low word)
;   BX = operand B
;   CX = operand A (copy)
;   DX = 0 (add/sub) | high word of product (mul) | remainder (div)
; ============================================================
INCLUDE Irvine32.inc

; ---- print a string without disturbing any register ----
SayStr MACRO s
    pushad
    mov  edx, OFFSET s
    call WriteString
    popad
ENDM

.data
msgTitle    BYTE "=== 16-bit Integer Calculator (MASM 32-bit) ===",0Dh,0Ah,0
msgA        BYTE "Enter first  number A (-32768 ~ 32767) : ",0
msgB        BYTE "Enter second number B (-32768 ~ 32767) : ",0
msgBad      BYTE "  >> Invalid input. Try again.",0Dh,0Ah,0
msgOvf      BYTE "  >> 16-bit overflow occurred (OF=1), AX is wrapped.",0Dh,0Ah,0
msgDivZero  BYTE "  >> Division by zero. Skipped.",0Dh,0Ah,0
msgDivOvf   BYTE "  >> -32768 / -1 overflows 16 bits. Skipped.",0Dh,0Ah,0
msgBye      BYTE 0Dh,0Ah,"Done.",0Dh,0Ah,0

hdrAdd      BYTE 0Dh,0Ah,"[1] Addition",0Dh,0Ah,0
hdrSub      BYTE 0Dh,0Ah,"[2] Subtraction",0Dh,0Ah,0
hdrMul      BYTE 0Dh,0Ah,"[3] Multiplication (IMUL, DX:AX)",0Dh,0Ah,0
hdrDiv      BYTE 0Dh,0Ah,"[4] Division (CWD + IDIV)",0Dh,0Ah,0

lblAdd      BYTE "  A + B = ",0
lblSub      BYTE "  A - B = ",0
lblMul      BYTE "  A * B = ",0
lblQuo      BYTE "  A / B = ",0
lblRem      BYTE "  A % B = ",0

sAX         BYTE "  AX = ",0
sBX         BYTE "  BX = ",0
sCX         BYTE "  CX = ",0
sDX         BYTE "  DX = ",0
sSigned     BYTE "h  (signed ",0
sUnsigned   BYTE ", unsigned ",0
sEnd        BYTE ")",0Dh,0Ah,0

numA        SWORD ?
numB        SWORD ?
ovf         BYTE  0
promptPtr   DWORD ?
wAX         WORD  ?
wBX         WORD  ?
wCX         WORD  ?
wDX         WORD  ?

.code
; ------------------------------------------------------------
; ReadNum : EDX = prompt offset, returns EAX = value (16-bit range)
; ------------------------------------------------------------
ReadNum PROC
    mov  promptPtr, edx
again:
    mov  edx, promptPtr
    call WriteString
    call ReadInt            ; OF=1 if input is not a valid 32-bit int
    jo   bad
    cmp  eax, -32768
    jl   bad
    cmp  eax, 32767
    jg   bad
    ret
bad:
    mov  edx, OFFSET msgBad
    call WriteString
    jmp  again
ReadNum ENDP

; ------------------------------------------------------------
; PrintRes16 : prints AX as signed decimal + CRLF (registers kept)
; ------------------------------------------------------------
PrintRes16 PROC
    pushad
    movsx eax, ax
    call WriteInt
    call Crlf
    popad
    ret
PrintRes16 ENDP

; ------------------------------------------------------------
; PrintRes32 : prints DX:AX as signed 32-bit decimal (registers kept)
; ------------------------------------------------------------
PrintRes32 PROC
    pushad
    movzx eax, ax
    shl   edx, 16
    or    eax, edx
    call  WriteInt
    call  Crlf
    popad
    ret
PrintRes32 ENDP

; ------------------------------------------------------------
; CheckOvf : prints warning if ovf flag variable is set
; ------------------------------------------------------------
CheckOvf PROC
    cmp  ovf, 0
    je   done
    SayStr msgOvf
done:
    ret
CheckOvf ENDP

; ------------------------------------------------------------
; PrintReg16 : EDX = name string, EAX = zero-extended 16-bit value
; (clobbers EAX,EBX,EDX - always called inside pushad/popad)
; ------------------------------------------------------------
PrintReg16 PROC
    push eax
    call WriteString        ; "  AX = "
    pop  eax
    push eax
    mov  ebx, 2             ; 2 bytes -> 4 hex digits
    call WriteHexB
    mov  edx, OFFSET sSigned
    call WriteString
    pop  eax
    push eax
    movsx eax, ax
    call WriteInt
    mov  edx, OFFSET sUnsigned
    call WriteString
    pop  eax
    call WriteDec
    mov  edx, OFFSET sEnd
    call WriteString
    ret
PrintReg16 ENDP

; ------------------------------------------------------------
; ShowRegs : prints current AX,BX,CX,DX (all registers preserved)
; ------------------------------------------------------------
ShowRegs PROC
    mov  wAX, ax
    mov  wBX, bx
    mov  wCX, cx
    mov  wDX, dx
    pushad
    mov  edx, OFFSET sAX
    movzx eax, wAX
    call PrintReg16
    mov  edx, OFFSET sBX
    movzx eax, wBX
    call PrintReg16
    mov  edx, OFFSET sCX
    movzx eax, wCX
    call PrintReg16
    mov  edx, OFFSET sDX
    movzx eax, wDX
    call PrintReg16
    popad
    ret
ShowRegs ENDP

; ============================================================
main PROC
    mov  edx, OFFSET msgTitle
    call WriteString
    call Crlf

    mov  edx, OFFSET msgA
    call ReadNum
    mov  numA, ax
    mov  edx, OFFSET msgB
    call ReadNum
    mov  numB, ax

    ; ---------------- [1] ADD ----------------
    SayStr hdrAdd
    mov  ax, numA
    mov  cx, ax
    mov  bx, numB
    xor  dx, dx
    add  ax, bx
    mov  ovf, 0             ; MOV does not touch flags
    jno  add_ok
    mov  ovf, 1
add_ok:
    SayStr lblAdd
    call PrintRes16
    call CheckOvf
    call ShowRegs

    ; ---------------- [2] SUB ----------------
    SayStr hdrSub
    mov  ax, numA
    mov  cx, ax
    mov  bx, numB
    xor  dx, dx
    sub  ax, bx
    mov  ovf, 0
    jno  sub_ok
    mov  ovf, 1
sub_ok:
    SayStr lblSub
    call PrintRes16
    call CheckOvf
    call ShowRegs

    ; ---------------- [3] MUL ----------------
    SayStr hdrMul
    mov  ax, numA
    mov  cx, ax
    mov  bx, numB
    imul bx                 ; DX:AX = AX * BX
    SayStr lblMul
    call PrintRes32
    call ShowRegs

    ; ---------------- [4] DIV ----------------
    SayStr hdrDiv
    cmp  numB, 0
    je   div_zero
    cmp  numA, -32768
    jne  div_go
    cmp  numB, -1
    je   div_ovf
div_go:
    mov  ax, numA
    mov  cx, ax
    mov  bx, numB
    cwd                     ; DX:AX = sign-extended AX
    idiv bx                 ; AX = quotient, DX = remainder
    SayStr lblQuo
    call PrintRes16
    xchg ax, dx
    SayStr lblRem
    call PrintRes16
    xchg ax, dx
    call ShowRegs
    jmp  finish
div_zero:
    SayStr msgDivZero
    jmp  finish
div_ovf:
    SayStr msgDivOvf

finish:
    SayStr msgBye
    exit
main ENDP
END main
