package il.org.spartan.refactoring.wring;

import static il.org.spartan.refactoring.utils.Funcs.*;
import static il.org.spartan.utils.Utils.*;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;
import il.org.spartan.refactoring.preferences.PluginPreferencesResources.*;
import il.org.spartan.refactoring.utils.*;

/** A {@link Wring} to convert
 *
 * <pre>
 * <b>if</b> (a) { f(); g(); }
 * </pre>
 *
 * into
 *
 * <pre>
 * <b>if</b> (!a) return f(); g();
 * </pre>
 *
 * provided that this
 *
 * <pre>
 * <b>if</b>
 * </pre>
 *
 * statement is the last statement in a method.
 * @author Yossi Gil
 * @since 2015-09-09 */
public class IfLastInMethod extends Wring<IfStatement> {
  @Override String description(final IfStatement s) {
    return "Invert conditional " + s.getExpression() + " for early return";
  }
  @Override Rewrite make(final IfStatement s) {
    if (Is.vacuousThen(s) || !Is.vacuousElse(s) || extract.statements(then(s)).size() < 2)
      return null;
    final Block b = asBlock(s.getParent());
    return b == null || !lastIn(s, b.statements()) || !(b.getParent() instanceof MethodDeclaration) ? null : new Rewrite(description(s), s) {
      @Override public void go(final ASTRewrite r, final TextEditGroup g) {
        Wrings.insertAfter(s, extract.statements(then(s)), r, g);
        final IfStatement newIf = duplicate(s);
        newIf.setExpression(duplicate(logicalNot(s.getExpression())));
        newIf.setThenStatement(s.getAST().newReturnStatement());
        newIf.setElseStatement(null);
        r.replace(s, newIf, g);
      }
    };
  }
  @Override WringGroup wringGroup() {
    return WringGroup.REFACTOR_INEFFECTIVE;
  }
}