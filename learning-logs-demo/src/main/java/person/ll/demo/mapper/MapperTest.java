package person.ll.demo.mapper;

@Mapper
public interface MapperTest {
    @Select("select id from table where id = {0}")
    public String select(@Param("id")String id);
}

