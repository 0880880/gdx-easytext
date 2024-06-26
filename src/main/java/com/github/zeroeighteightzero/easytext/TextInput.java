package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class TextInput extends InputAdapter {

    public String text = "";

    public boolean focused = false;
    public boolean disabled = false;

    public int caretPositionStart = 0;
    public int caretPositionEnd = 0;

    private boolean shiftKey = false;
    private boolean ctrlKey = false;
    private boolean rightKey = false;
    private boolean leftKey = false;
    private boolean backspaceKey = false;
    private boolean deleteKey = false;
    private boolean homeKey = false;
    private boolean endKey = false;
    private boolean insertKey = false;
    private boolean aKey = false;
    private boolean cKey = false;
    private boolean xKey = false;
    private boolean vKey = false;

    public void setCaretPosition(int caretPosition) {
        this.caretPositionStart = caretPosition;
        this.caretPositionEnd = caretPosition;
    }

    // TODO
    // TODO - Selection
    // TODO

    public boolean keyDown(int keycode) {
        if (focused && !disabled) {
            if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) shiftKey = true;
            if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) ctrlKey = true;

            if (keycode == Input.Keys.HOME && !homeKey) {
                if (shiftKey) caretPositionStart = 0;
                else setCaretPosition(0);
                homeKey = true;
            }
            if (keycode == Input.Keys.END && !endKey) {
                if (shiftKey) caretPositionEnd = text.length() - 1;
                else setCaretPosition(text.length() - 1);
                endKey = true;
            }
            if (keycode == Input.Keys.RIGHT && !rightKey) {
                if (shiftKey) {
                    caretPositionEnd++;
                } else {
                    if (caretPositionEnd != caretPositionStart) {
                        caretPositionStart = caretPositionEnd;
                    } else {
                        caretPositionStart++;
                        caretPositionEnd++;
                    }
                }
                rightKey = true;
            } else if (keycode == Input.Keys.LEFT && !leftKey) {
                if (shiftKey) {
                    caretPositionStart--;
                } else {
                    if (caretPositionEnd != caretPositionStart) {
                        caretPositionEnd = caretPositionStart;
                    } else {
                        caretPositionStart--;
                        caretPositionEnd--;
                    }
                }
                leftKey = true;
            }
            if (keycode == Input.Keys.BACKSPACE && !backspaceKey && !text.isEmpty()) {
                text = text.substring(0, caretPositionStart - 1) + text.substring(caretPositionEnd);
                if (caretPositionStart != caretPositionEnd) {
                    caretPositionEnd = caretPositionStart;
                } else {
                    caretPositionStart--;
                    caretPositionEnd--;
                }
                backspaceKey = true;
            }
            if (keycode == Input.Keys.FORWARD_DEL && !deleteKey && !text.isEmpty()) {
                text = text.substring(0, caretPositionStart) + text.substring(caretPositionEnd != caretPositionStart ? caretPositionEnd : caretPositionEnd + 1);
                deleteKey = true;
            }
            if (ctrlKey) {
                if (keycode == Input.Keys.C && caretPositionStart != caretPositionEnd && !cKey) {
                    Gdx.app.getClipboard().setContents(text.substring(caretPositionStart, caretPositionEnd));
                    cKey = true;
                }
                if (keycode == Input.Keys.INSERT && caretPositionStart != caretPositionEnd && !insertKey) {
                    Gdx.app.getClipboard().setContents(text.substring(caretPositionStart, caretPositionEnd));
                    insertKey = true;
                }
                if (keycode == Input.Keys.X && caretPositionStart != caretPositionEnd && !xKey) {
                    if (Gdx.app.getClipboard().hasContents()) {
                        text = text.substring(0,caretPositionStart) + Gdx.app.getClipboard().getContents() + text.substring(caretPositionEnd);
                        setCaretPosition(caretPositionStart);
                    }
                    xKey = true;
                }
                if (keycode == Input.Keys.V && !vKey) {
                    text = text.substring(0,caretPositionStart) + text.substring(caretPositionEnd);
                    setCaretPosition(caretPositionStart + 1);
                    vKey = true;
                }
                if (keycode == Input.Keys.A && !aKey) {
                    caretPositionStart = 0;
                    caretPositionEnd = text.length() - 1;
                    aKey = true;
                }
            }
            if (shiftKey && keycode == Input.Keys.INSERT && !insertKey) {
                text = text.substring(0,caretPositionStart) + text.substring(caretPositionEnd);
                setCaretPosition(caretPositionStart + 1);
                insertKey = true;
            }

            caretPositionStart = Math.max(Math.min(caretPositionStart, text.length() - 1), 0);
            caretPositionEnd = Math.max(Math.min(caretPositionEnd, text.length() - 1), 0);
            if (caretPositionEnd > caretPositionStart) {
                int end = caretPositionEnd;
                caretPositionEnd = caretPositionStart;
                caretPositionStart = end;
            }
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        if (focused && !disabled) {
            if (keycode == Input.Keys.RIGHT && rightKey)
                rightKey = false;
            else if (keycode == Input.Keys.LEFT && leftKey)
                leftKey = false;
            if (keycode == Input.Keys.BACKSPACE && backspaceKey)
                backspaceKey = false;
            if (keycode == Input.Keys.FORWARD_DEL && deleteKey)
                deleteKey = false;
            if (keycode == Input.Keys.C && cKey)
                cKey = false;
            if (keycode == Input.Keys.X && xKey)
                xKey = false;
            if (keycode == Input.Keys.V && vKey)
                vKey = false;
            if (keycode == Input.Keys.A && aKey)
                aKey = false;
            if (keycode == Input.Keys.INSERT && insertKey)
                insertKey = false;
            if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) shiftKey = false;
            if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) ctrlKey = false;
        }
        return false;
    }

    public boolean keyTyped(char character) {
        if (focused && !disabled && !ctrlKey) {
            text = text.substring(0, caretPositionStart) + character + text.substring(caretPositionEnd);
            caretPositionEnd = caretPositionStart;
        }
        return true;
    }

}
