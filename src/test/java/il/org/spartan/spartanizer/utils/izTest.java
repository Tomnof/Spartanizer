package il.org.spartan.spartanizer.utils;

import static il.org.spartan.spartanizer.engine.into.*;
import static org.eclipse.jdt.core.dom.ASTNode.*;

import org.junit.*;

import static il.org.spartan.spartanizer.ast.navigate.extract.*;

import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.java.*;

/** Test class for class {@link iz}
 * @author Yossi Gil
 * @since 2015-07-17 */
@SuppressWarnings({ "javadoc", "static-method" }) //
public final class izTest {
  @Test public void booleanLiteralFalseOnNull() {
    assert !iz.booleanLiteral(e("null"));
  }

  @Test public void booleanLiteralFalseOnNumeric() {
    assert !iz.booleanLiteral(e("12"));
  }

  @Test public void booleanLiteralFalseOnThis() {
    assert !iz.booleanLiteral(e("this"));
  }

  @Test public void booleanLiteralTrueOnFalse() {
    assert iz.booleanLiteral(e("false"));
  }

  @Test public void booleanLiteralTrueOnTrue() {
    assert iz.booleanLiteral(e("true"));
  }

  @Test public void callIsSpecificTrue() {
    assert iz.constant(e("this"));
  }

  @Test public void canMakeExpression() {
    e("2+2");
  }

  @Test public void deterministicArray1() {
    assert !sideEffects.deterministic(e("new a[3]"));
  }

  @Test public void deterministicArray2() {
    assert !sideEffects.deterministic(e("new int[] {12,13}"));
  }

  @Test public void deterministicArray3() {
    assert !sideEffects.deterministic(e("new int[] {12,13, i++}"));
  }

  @Test public void deterministicArray4() {
    assert !sideEffects.deterministic(e("new int[f()]"));
  }

  @Test public void isConstantFalse() {
    assert !iz.constant(e("a"));
  }

  @Test public void isNullFalse1() {
    assert !iz.nullLiteral(e("this"));
  }

  @Test public void isNullFalse2() {
    assert !iz.thisLiteral(e("this.a"));
  }

  @Test public void isNullTrue() {
    assert iz.nullLiteral(e("null"));
  }

  @Test public void isOneOf() {
    assert iz.nodeTypeIn(e("this"), new int[] { CHARACTER_LITERAL, NUMBER_LITERAL, NULL_LITERAL, THIS_EXPRESSION });
  }

  @Test public void isThisFalse1() {
    assert !iz.thisLiteral(e("null"));
  }

  @Test public void isThisFalse2() {
    assert !iz.thisLiteral(e("this.a"));
  }

  @Test public void isThisTrue() {
    assert iz.thisLiteral(e("this"));
  }

  @Test public void negative0() {
    assert !iz.negative(e("0"));
  }

  @Test public void negative1() {
    assert !iz.negative(e("0"));
  }

  @Test public void negativeMinus1() {
    assert iz.negative(e("- 1"));
  }

  @Test public void negativeMinus2() {
    assert iz.negative(e("- 2"));
  }

  @Test public void negativeMinusA() {
    assert iz.negative(e("- a"));
  }

  @Test public void negativeNull() {
    assert !iz.negative(e("null"));
  }

  @Test public void nonnAssociative() {
    final boolean b = wizard.nonAssociative(e("1"));
    assert !b;
    final boolean b1 = wizard.nonAssociative(e("-1"));
    assert !b1;
    final boolean b2 = wizard.nonAssociative(e("-1+2"));
    assert !b2;
    final boolean b3 = wizard.nonAssociative(e("1+2"));
    assert !b3;
    final boolean b4 = wizard.nonAssociative(e("2-1"));
    assert b4;
    final boolean b5 = wizard.nonAssociative(e("2/1"));
    assert b5;
    final boolean b6 = wizard.nonAssociative(e("2%1"));
    assert b6;
    assert !wizard.nonAssociative(e("2*1"));
  }

  @Test public void numericLiteralFalse1() {
    assert !iz.numericLiteral(e("2*3"));
  }

  @Test public void numericLiteralFalse2() {
    assert !iz.numericLiteral(e("2*3"));
  }

  @Test public void numericLiteralTrue() {
    assert iz.numericLiteral(e("1"));
  }

  @Test public void seriesA_3() {
    assert !iz.infixPlus(e("(i+j)"));
    assert iz.infixPlus(core(e("(i+j)")));
    assert !iz.infixMinus(e("(i-j)"));
    assert iz.infixMinus(core(e("(i-j)")));
  }
}
