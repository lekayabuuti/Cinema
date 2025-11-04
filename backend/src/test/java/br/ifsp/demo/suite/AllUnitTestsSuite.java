package br.ifsp.demo.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("br.ifsp.demo") // faz do pacote base de testes inteiro
@IncludeTags("UnitTest")         //roda TUDO que tiver a tag "UnitTest"
public class AllUnitTestsSuite {
}