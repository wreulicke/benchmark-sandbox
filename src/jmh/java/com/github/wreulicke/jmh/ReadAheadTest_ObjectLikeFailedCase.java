package com.github.wreulicke.jmh;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Map;

@State(Scope.Benchmark)
public class ReadAheadTest_ObjectLikeFailedCase {

  ObjectMapper mapper;

  private String json;

  @Setup(Level.Iteration)
  public void setupInIteration() {
    Generator generator = new Generator();
    json = generator.generate();
  }

  @Setup
  public void setup() {
    mapper = new ObjectMapper();
  }

  interface ITake {
    public Object take(String json);
  }

  class NaiveImpl implements ITake {

    @Override
    public Object take(String json) {
      try {
        Map<String, Object> m = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        return m;
      } catch (IOException e) {
        return json;
      }
    }
  }

  class ReadAheadImpl implements ITake {
    public Object take(String json) {
      if (json.startsWith("{") && json.endsWith("}")) {
        try {
          Map<String, Object> m = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
          });
          return m;
        } catch (IOException e) {
          return json;
        }
      }
      return json;
    }
  }

  class ReadAheadImpl2 implements ITake {
    public Object take(String json) {
      if (json.startsWith("{") && json.endsWith("}")) {
        try {
          JsonNode jsonNode = mapper.readTree(json);
          return jsonNode;
        } catch (IOException e) {
          return json;
        }
      }
      return json;
    }
  }

  class ReadAheadImpl3 implements ITake {
    public Object take(String json) {
      if (json.startsWith("{") && json.endsWith("}")) {
        try {
          Map m = mapper.readValue(json, Map.class);
          return m;
        } catch (IOException e) {
          return json;
        }
      }
      return json;
    }
  }

  @State(Scope.Benchmark)
  static class Generator {

    String generate() {
      if (Math.random() < 0.1) {
        return "{\n" +
          "  \"_id\": \"5a1d3585d22734fd417769ee\",\n" +
          "  \"index\": 0,\n" +
          "  \"guid\": \"37441f05-d31f-4f58-a3f8-c64cf3f5278a\",\n" +
          "  \"isActive\": true,\n" +
          "  \"balance\": \"$3,345.47\",\n" +
          "  \"picture\": \"http://placehold.it/32x32\",\n" +
          "  \"age\": 38,\n" +
          "  \"eyeColor\": \"brown\",\n" +
          "  \"name\": \"Salinas Vang\",\n" +
          "  \"gender\": \"male\",\n" +
          "  \"company\": \"CODAX\",\n" +
          "  \"email\": \"salinasvang@codax.com\",\n" +
          "  \"phone\": \"+1 (945) 467-2910\",\n" +
          "  \"address\": \"191 Engert Avenue, Johnsonburg, Connecticut, 114\",\n" +
          "  \"about\": \"Duis duis et irure voluptate. Voluptate ea eu veniam cillum amet Lorem commodo amet qui commodo consectetur. Sit velit ullamco aliqua amet in duis nulla. Lorem proident occaecat enim nostrud velit deserunt commodo occaecat laboris. Tempor enim tempor duis sunt est sint tempor incididunt tempor.\\r\\nDolor aute sit eu amet non mollit deserunt ad cupidatat irure. Nisi minim fugiat enim irure tempor ad irure voluptate nulla veniam dolor. Est aute duis velit ullamco cupidatat reprehenderit aute laborum mollit do dolor adipisicing.\\r\\nAnim culpa nisi dolore reprehenderit sit Lorem commodo in minim pariatur esse aute ex veniam. Id ipsum id est id. Et ad ex reprehenderit velit ex aliquip exercitation nisi in laborum exercitation magna ullamco do. In dolor labore tempor laboris nostrud labore qui sint et aliqua magna tempor ex. Proident mollit aliqua id veniam non duis consequat nulla irure fugiat proident fugiat duis. Amet ut et dolore anim do officia ut amet laboris enim ea culpa duis. Nostrud eu cupidatat eu qui amet enim ullamco mollit.\\r\\nEu quis sit excepteur deserunt exercitation eu excepteur duis. Duis incididunt ipsum amet excepteur cillum anim quis deserunt aliquip ea aliqua reprehenderit consectetur aliquip. Esse deserunt duis laboris laboris ea amet et anim et dolor voluptate est do labore.\\r\\nVeniam ipsum mollit sit ad magna et nisi enim ullamco officia. Laborum qui laborum sit tempor voluptate. Nisi nisi non voluptate do mollit.\\r\\nUt labore eiusmod incididunt nostrud tempor aliqua reprehenderit laboris id deserunt anim minim sit labore. Dolore veniam nisi sit deserunt qui. Magna dolor qui minim id do proident anim Lorem adipisicing. Commodo eiusmod deserunt nulla laboris anim cillum Lorem Lorem. Velit fugiat nisi labore pariatur commodo reprehenderit sunt ex. Fugiat cillum aute commodo deserunt exercitation eu. Consectetur amet ipsum est magna qui in fugiat voluptate aliquip non cillum non.\\r\\nAliquip duis minim nisi velit do fugiat et incididunt ad ipsum. Ullamco dolore do elit adipisicing velit in ex incididunt voluptate ea ut qui occaecat ullamco. Ipsum nulla officia adipisicing esse irure ipsum et deserunt est labore adipisicing esse consequat.\\r\\nFugiat minim ex sint adipisicing deserunt labore ullamco enim. Ea aute nulla exercitation ut velit duis laboris incididunt mollit amet ipsum elit exercitation ad. Quis aute nulla Lorem fugiat deserunt commodo nisi id amet ea mollit ipsum ea. Elit adipisicing proident aliquip enim consequat cupidatat amet. Nostrud occaecat occaecat ipsum velit amet consequat. Fugiat pariatur mollit nostrud exercitation sunt cillum eiusmod sint ex pariatur ut magna ullamco. Consectetur ex laborum elit officia excepteur incididunt commodo.\\r\\nDolore exercitation reprehenderit ipsum occaecat in nisi qui non in mollit. Irure dolore tempor tempor non adipisicing labore nisi eu exercitation duis. Dolore in tempor cillum nulla mollit officia. Enim velit enim duis aliqua eu proident ullamco exercitation id nisi non. Minim id labore officia eu voluptate aliquip adipisicing fugiat non ut. Labore voluptate et occaecat velit in id exercitation qui.\\r\\nLaboris nisi duis do ullamco fugiat consequat culpa sint occaecat sunt amet mollit. Elit nulla mollit magna in veniam cillum deserunt consequat elit quis proident culpa. Consectetur dolore ex velit minim cillum proident fugiat aliqua est id quis reprehenderit. Adipisicing velit in nulla aliqua irure dolor cupidatat pariatur. Voluptate adipisicing magna eiusmod deserunt dolor qui aute laborum est. Lorem labore deserunt ea Lorem amet exercitation magna amet ipsum. Cupidatat quis occaecat exercitation sunt aliquip Lorem reprehenderit commodo nulla ullamco minim eu nulla.\\r\\nDolore non commodo sit ipsum consequat consectetur labore adipisicing dolore fugiat aliqua irure. Esse minim ea et incididunt nisi eu proident amet eu. Pariatur sit eiusmod aliqua culpa amet dolor sunt minim in consectetur consectetur ex deserunt non. Aliqua cupidatat duis elit enim aute cupidatat veniam ad irure mollit deserunt magna. Voluptate minim est eu culpa. Aliqua proident sunt sint quis officia cupidatat ad elit. Laborum pariatur consequat ex consectetur sunt eiusmod.\\r\\nCupidatat et sunt cupidatat Lorem. Do aute ex officia exercitation aliquip id aute pariatur fugiat est cillum fugiat eu labore. Laboris nulla elit esse et velit ut qui dolore laboris commodo labore consectetur ex. Qui laboris mollit tempor elit.\\r\\nAdipisicing consectetur consequat est veniam enim id ullamco aute pariatur ut aute. Velit ut excepteur aliqua esse cillum. Do mollit nostrud laborum ut aute do id cupidatat cupidatat sunt incididunt. Lorem labore nisi duis duis occaecat amet sit sunt fugiat velit fugiat mollit.\\r\\nExcepteur laboris tempor aliquip incididunt cillum cillum sit aliquip incididunt fugiat aliqua ad laborum. Labore non est esse id pariatur commodo officia elit esse eu sunt dolore in ex. Laboris esse officia esse sit sint velit consectetur eu eiusmod cupidatat aliqua ex sint. Ut sit tempor voluptate sit eiusmod labore aliquip fugiat proident occaecat et. Dolore sunt laborum laborum aliquip irure est velit occaecat deserunt duis magna laboris voluptate anim.\\r\\nAdipisicing dolore et est exercitation laboris ullamco ipsum laboris deserunt anim dolore. Ut consequat labore consectetur consequat qui laborum veniam aute cillum officia culpa ut duis. Aute eu voluptate ullamco fugiat duis. Consectetur laboris ad officia pariatur nostrud enim excepteur ea. Velit mollit amet Lorem incididunt voluptate officia proident irure aute aliqua sit ut. Excepteur ad commodo irure reprehenderit. Minim mollit magna ea tempor cillum nulla excepteur eu labore quis aliqua nostrud veniam culpa.\\r\\nOccaecat duis non elit amet eiusmod. Incididunt tempor labore duis tempor. Aliqua in consectetur non ea et sit laborum ad. Ea cupidatat pariatur dolor nulla veniam ullamco duis. Dolor eu aute dolor cillum ea non incididunt eu exercitation eu dolore et ad dolore.\\r\\nMollit aute dolor laborum consectetur ex pariatur. Nostrud enim et occaecat exercitation labore consectetur elit ut non amet in amet. Voluptate dolor commodo quis Lorem. Adipisicing amet non aliquip commodo quis commodo sint sit duis dolore.\\r\\nOccaecat exercitation occaecat qui et sunt ipsum deserunt nostrud adipisicing in. Magna Lorem reprehenderit sunt laborum exercitation anim dolore exercitation ad magna. Id qui in pariatur nisi laborum mollit cupidatat sint. Velit laboris qui fugiat irure culpa amet.\\r\\nQui exercitation officia nulla enim occaecat do dolore ea ipsum adipisicing excepteur adipisicing mollit pariatur. Laborum sint irure ut pariatur nulla consequat esse. Quis non in velit ullamco aliqua officia cillum ullamco ad irure. Pariatur do nostrud sint exercitation. Non excepteur fugiat veniam laboris culpa. Magna exercitation sunt esse et. Magna cillum irure amet veniam eu.\\r\\nVoluptate pariatur do sint exercitation nulla consequat sit minim. Nisi proident cillum aliqua ipsum laboris. Labore aliqua voluptate sint reprehenderit nostrud quis do sit id cupidatat consequat velit. Nisi magna velit in duis eu. Aliqua non ut enim excepteur fugiat. Ipsum id dolore consectetur aliqua labore in.\\r\\n\",\n" +
          "  \"registered\": \"2014-03-20T05:37:29 -09:00\",\n" +
          "  \"latitude\": -40.916377,\n" +
          "  \"longitude\": -80.052677,\n" +
          "  \"tags\": [\n" +
          "    \"ex\",\n" +
          "    \"consequat\",\n" +
          "    \"labore\",\n" +
          "    \"velit\",\n" +
          "    \"nulla\",\n" +
          "    \"anim\",\n" +
          "    \"cillum\"\n" +
          "  ],\n" +
          "  \"friends\": [\n" +
          "    {\n" +
          "      \"id\": 0,\n" +
          "      \"name\": \"Burton Snider\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"id\": 1,\n" +
          "      \"name\": \"Barron Young\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"id\": 2,\n" +
          "      \"name\": \"Harris Mathis\"\n" +
          "    }\n" +
          "  ],\n" +
          "  \"greeting\": \"Hello, Salinas Vang! You have 7 unread messages.\",\n" +
          "  \"favoriteFruit\": xapplex\n" +
          "}";
      } else {
        return "Lorem ipsum dolor sit amet, tota tibique sit no, doming menandri evertitur nam an. Odio quot maiorum at quo. Qui eu nonumy appareat, eam eu maluisset persequeris, at vel suavitate disputando contentiones. Audiam scripserit ei vix. At est porro melius copiosae, ludus ubique te quo.\\nQuo duis graece torquatos at. Sit at rebum libris neglegentur, ea mei ferri commodo tacimates. Ea cum veri facer sententiae, per purto liber no, mutat latine malorum te vis. Sumo sententiae intellegebat ei eos. No qui adhuc oratio prompta. Pri at amet summo, est oratio nonumes comprehensam an, cu facer ponderum insolens eos.\\n\\nNe justo cetero honestatis ius. In adhuc labore electram cum, et quot natum doctus quo. In his quot officiis necessitatibus. Tollit pericula inciderint ad mea, possim volumus maluisset te cum, probo percipit ad ius. Vel complectitur voluptatibus interpretaris at. Impedit necessitatibus an nam, ad cum mucius maiorum imperdiet.\\n\\nHis ea dolores quaestio iracundia, usu ei agam eloquentiam. Sint maluisset cotidieque te eam, an dicta decore vim. Ut est liber dolore semper, cu quidam accusamus gubergren usu. Delectus intellegebat mel ei, in inani efficiendi consequuntur pri. Cum ea tacimates mandamus delicatissimi, ad diam philosophia vel. Putent meliore nominavi at sed, lobortis voluptatum ad duo, cu cum harum admodum nusquam.\\n\\nFabulas denique mediocritatem ne ius, his ea liber altera, summo contentiones sed no. Ne nec etiam falli verear, eu adhuc forensibus cum, ad utroque legendos sea. Scripta saperet te pro. Ea vel ullum accusam recusabo, antiopam dissentiunt an mel. Cum id omnium pertinacia, eros salutatus ne cum, mea debet elitr ut.";
      }
    }
  }

  @Benchmark
  public void case1() {
    new NaiveImpl().take(json);
  }

  @Benchmark
  public void case2() {
    new ReadAheadImpl().take(json);
  }

  @Benchmark
  public void case3() {
    new ReadAheadImpl2().take(json);
  }
  
  @Benchmark
  public void case4() {
    new ReadAheadImpl3().take(json);
  }
}
