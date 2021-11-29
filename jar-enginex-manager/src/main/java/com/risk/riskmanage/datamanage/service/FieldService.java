package com.risk.riskmanage.datamanage.service;

import com.risk.riskmanage.datamanage.model.Field;
import com.risk.riskmanage.common.model.requestParam.UpdateFolderParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FieldService {
	
	/**
	 * createField:(插入新字段). <br/>
	 * 
	 *
	 * @param fieldVo
	 *            字段实体类
	 * @return 字段插入成功
	 * */
	public boolean createField(Field fieldVo, Map<String, Object> paramMap);

	/**
	 * isExists:(查找字段是否存在). <br/>
	 * 
	 *
	 * @param paramMap
	 *            参数集合
	 * @return 存在的记录条数
	 */
	public int isExists(Map<String, Object> paramMap);
	
	/**
	 * isExistsFieldType:(查找字段类型是否存在). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 存在的记录条数
	 */
	public int isExistsFieldType(Map<String,Object> paramMap);
	
	/**
	 * isExistsDefaultTreeName:(查找默认节点名是否存在). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 存在的记录条数
	 */
	public int isExistsDefaultTreeName(Map<String,Object> paramMap);

	/**
	 * updateField:(修改字段内容). <br/>
	 * 
	 *
	 * @param fieldVo
	 *            字段实体类
	 * @return 更新成功
	 */
	public boolean updateField(Map<String, Object> paramMap);

	/**
	 * findByFieldType:(按照字段类型查找字段列表). <br/>
	 * 
	 *
	 * @param paramMap
	 *            参数集合
	 * @return 更新成功
	 */
	public List<Field> findByFieldType(Map<String, Object> paramMap);
	
	/**
	 * findFieldByIds:(找出一批字段id对应的字段列表). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段列表
	 */
	public List<Field> findFieldByIds(Map<String,Object> paramMap);
	
	/**
	 * searchByName:(模糊查找该用户可用字段列表). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public List<Field> searchByName(Map<String,Object> paramMap);
	
	/**
	 * findExcelByFieldType:(获取导出Excel需要的字段信息). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 
	 */
	public  List<Field> findExcelByFieldType(Map<String,Object> paramMap);
	
	/**
	 * findByFieldId:(根据Id查找字段). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段列表
	 */
	public Field findByFieldId(Map<String,Object> paramMap);
	
	/**
	 * findByFieldEn:(根据引擎和字段英文名找出引擎所用字段对象). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段对象
	 */
	public Field findByFieldEn(Map<String,Object> paramMap);
	
	/**
	 * findFieldTypeId:(根据传入参数查找字段类型ID). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新成功 
	 * */
	public Long findFieldTypeId(Map<String,Object> paramMap);
	
	/**
	 * findByUser:(查找用户可用字段). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public List<Field> findByUser(Map<String,Object> paramMap);

	
	/**
	 * updateStatus:(单个或批量更新用户字段关系). <br/>
	 * 
	 *
	 * @param paramMap
	 *            参数集合
	 * @return 插入成功
	 * */
	//public boolean updateStatus(Map<String, Object> paramMap);
	public Map<String, Object> updateStatus(Map<String, Object> paramMap);
	
	/**
	 * checkField:(公共方法：检查字段是否被引用). <br/>
	 * 
	 *
	 * @param paramMap
	 *            参数集合
	 * @return 结果集合
	 * */
	public Map<String, Object> checkField(Map<String, Object> paramMap);
	
	/**
	 * getSourceField:(公共方法：检查字段的所有构成字段). <br/>
	 * 
	 *
	 * @param paramMap
	 *            参数集合
	 * @return
	 * */
	public String getSourceField(String fieldIds,String fieldId);
	
	/**
	 * checkFieldType:(公共方法：检查字段类型及其下所有子类型的所有字段是否被引用). <br/>
	 * 
	 *
	 * @param paramMap
	 *            参数集合
	 * @return 结果集合
	 * */
	public Map<String, Object> checkFieldType(Map<String, Object> paramMap);
	
	/**
	 * findFieldIdsByTypeIds:(找出一批字段类型id对应的字段id列表). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段id逗号分隔字符串
	 */
	public String findFieldIdsByTypeIds(Map<String,Object> paramMap);
	
	/**
	 * findOrgFieldIdsByTypeIds:(在通用字段里找出一批字段类型id的字段id列表). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段列表
	 */
	public String findOrgFieldIdsByTypeIds(Map<String,Object> paramMap);
	
	/**
	 * importExcel:(导入excel). <br/>
	 * 
	 *
	 * @param url
	 * @param paramMap 
	 * 			     参数集合
	 * @return 导入成功
	 * */
	Map<String, Object> importExcel(String url,Map<String, Object> paramMap);

	/**
	 * getFieldList:(获取组织的所有字段). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 
	 */
	public  List<Field> getFieldList(Map<String,Object> paramMap);
	
	/**
	 * createEngineField:(批量拷贝组织通用字段到引擎字段). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 是否创建成功
	 */
	public  boolean createEngineField(Map<String,Object> paramMap);
	
	/**
	 * bindEngineField:(根据传入的engineId和字段id找到一连串的字段并绑定关系). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 是否创建成功
	 */
	public boolean bindEngineField(Map<String, Object> paramMap);
	
	/**
	 * findByFieldEn:(根据引擎和字段中文名找出引擎所用字段对象). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段对象
	 */
	public Field findByFieldCn(Map<String,Object> paramMap);
	
	/**
	 * getField:(根据引擎和字段中文名找出引擎所用字段对象). <br/>
	 *
	 * @param
	 * @return 字段对象
	 */
    public String getField(String fieldIds,String usedFieldId,String engineId);
    
    /**
	 * 查找某字段被引用的字段id并拼成逗号分隔的字符串
	 *
	 * @return
	 */
    public String getAllFieldTypeId(String ids,String pid,String engineId);
    
    /**
	 * 查找某字段类型所有的<父类型>拼成逗号分隔的字符串
	 *
	 * @return
	 */
    public String getAllParentFieldTypeId(String ids,String id,String engineId);
    
    /**
	 * 删除指定字段类型节点
	 *
	 * @return
	 */
    public Map<String,Object> deleteNode(@RequestParam Map<String,Object> paramMap);
    
    /**
	 * 测试用：构造数据填写样本数据
	 *
	 * @return
	 */
	public String createEngineTestData(@RequestParam Map<String,Object> paramMap);
	
//	public String createEngineTestResultPdf(@RequestParam Map<String,Object> paramMap);

	int fieldValidate(String fieldEn, String fieldCn, String engineId, Long fieldId);

    int updateFieldFolder(UpdateFolderParam param);

    String getFieldEnById(Long id);

    List<Field> queryByIds(Collection<Long> ids );
    List<Field> queryByEns(Collection<String> ens );
    List<Field> queryByOrganAndCns(Collection<String> cns ,Long organId);
	void sqlFieldCheck(Map map);
}
