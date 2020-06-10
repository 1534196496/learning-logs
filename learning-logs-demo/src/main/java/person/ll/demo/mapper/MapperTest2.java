package person.ll.demo.mapper;

import java.util.List;

@Mapper
public
interface MapperTest2 {
    @Select("select id from table where id = 1")
    public List<String> select();
}