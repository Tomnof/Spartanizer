package il.org.spartan.spartanizer.ast;

import static il.org.spartan.spartanizer.ast.step.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.*;

/** An empty <code><b>enum</b></code> for fluent programming. The name should
 * say it all: The name, followed by a dot, followed by a method name, should
 * read like a sentence phrase.
 * @author Yossi Gil
 * @since 2016-09-12 */
public enum haz {
  ;
  public static boolean dollar(final List<SimpleName> ns) {
    for (final SimpleName ¢ : ns)
      if ("$".equals(identifier(¢)))
        return true;
    return false;
  }

   public static boolean variableDefinition(final ASTNode n) {
    final Wrapper<Boolean> $ = new Wrapper<>(Boolean.FALSE);
    n.accept(new ASTVisitor() {
      @Override public boolean visit(final EnumConstantDeclaration ¢) {
        return continue¢(¢.getName());
      }

      @Override public boolean visit(final FieldDeclaration node) {
        return continue¢(fragments(node));
      }

      @Override public boolean visit(final SingleVariableDeclaration node) {
        return continue¢(node.getName());
      }

      @Override public boolean visit(final VariableDeclarationExpression node) {
        return continue¢(fragments(node));
      }

      @Override public boolean visit(final VariableDeclarationFragment f) {
        return continue¢(f.getName());
      }

      @Override public boolean visit(final VariableDeclarationStatement s) {
        return continue¢(fragments(s));
      }

      private boolean continue¢(final List<VariableDeclarationFragment> fs) {
        for (final VariableDeclarationFragment f : fs)
          if (!continue¢(f.getName()))
            return false;
        return true;
      }

      private boolean continue¢(final SimpleName name) {
        if (iz.identifier("$", name))
          return false;
        $.set(Boolean.TRUE);
        return true;
      }
    });
    return $.get().booleanValue();
  }
}
