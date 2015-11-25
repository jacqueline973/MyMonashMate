/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monash.mymonashmate.server.service;

import com.monash.mymonashmate.server.Course;
import com.monash.mymonashmate.server.CourseUnit;
import com.monash.mymonashmate.server.MatchCriteria;
import com.monash.mymonashmate.server.MatchMate;
import com.monash.mymonashmate.server.MatchMateResult;
import com.monash.mymonashmate.server.Profile;
import com.monash.mymonashmate.server.Student;
import com.monash.mymonashmate.server.StudentCourse;
import com.monash.mymonashmate.server.StudentCoursePK;
import com.monash.mymonashmate.server.StudentSetting;
import com.monash.mymonashmate.server.StudentSettingPK;
import com.monash.mymonashmate.server.StudentUnit;
import com.monash.mymonashmate.server.StudentUnitPK;
import com.monash.mymonashmate.server.Unit;
import com.monash.mymonashmate.server.UserAccess;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author betty
 */
public abstract class AbstractFacade<T> {
    
   public static final String SUBURB = "SUBURB";
   public static final String FAVOURITE_FOOD = "FAVOURITE_FOOD";
   public static final String NATIVE_LANGUAGE = "NATIVE_LANGUAGE";
   public static final String NICKNAME = "NICKNAME";

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T createReturn(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findAllUnitsByStudentId(String studentId) {
        Query query = getEntityManager().createNamedQuery("Unit.findAllByStudentId");
        query.setParameter("studentId", Integer.valueOf(studentId));
        return query.getResultList();
    }

    public List<T> findAllCoursesByStudentId(String studentId) {
        Query query = getEntityManager().createNamedQuery("Course.findAllByStudentId");
        query.setParameter("studentId", Integer.valueOf(studentId));
        return query.getResultList();
    }

    public T findUserByName(String name) {
        Query query = getEntityManager().createNamedQuery("UserAccess.findByName");
        query.setParameter("name", name);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            return (T) results.get(0);
        }
        return null;
    }

    public List<Unit> findUnitsByCourse(String[] courseCodeArray) {
        Query query = getEntityManager().createNamedQuery("CourseUnit.findByCourseCode");
        Query unitQuery = getEntityManager().createNamedQuery("Unit.findByUnitCode");
        List<Unit> units = new ArrayList<Unit>();
        Set<String> unitCodeSet = new HashSet<String>();
        for (int i = 0; i < courseCodeArray.length; i++) {
            query.setParameter("courseCode", courseCodeArray[i]);
            List courseUnit = query.getResultList();
            if (!courseUnit.isEmpty()) {
                getUnits(courseUnit, unitQuery, units, unitCodeSet);
            }
        }
        return units;
    }

    private void getUnits(List courseUnit, Query unitQuery, List<Unit> units, Set<String> unitCodeSet) {
        List<CourseUnit> courseUnitList = (List<CourseUnit>) courseUnit;
        for (CourseUnit cu : courseUnitList) {
            unitQuery.setParameter("unitCode", cu.getCourseUnitPK().getUnitCode());
            List unitList = unitQuery.getResultList();
            if (!unitList.isEmpty()) {
                Unit unit = (Unit) unitList.get(0);
                if (unitCodeSet.add(unit.getUnitCode())) {
                    units.add(unit);
                }
            }
        }
    }

    public MatchMateResult matchStudents(MatchCriteria criteria) {
        MatchMateResult result = new MatchMateResult();
        List<MatchMate> newList = new ArrayList<MatchMate>();
        String unitCode = criteria.getUnitCodes();
        String courseCode = criteria.getCourseCodes();
        String nativeLanguage = criteria.getNativeLanguage();
        String favouriteFood = criteria.getFavouriteFood();
        String suburb = criteria.getSuburb();
        Integer currentUserStudentId = Integer.valueOf(criteria.getCurrentUserStudentId());
  
        if (null != unitCode) {
            newList = getMatchMateListByUnitCode(criteria, newList);
            if (null != courseCode) {
                newList = getMatchResultByCourseCode(false, newList, courseCode, currentUserStudentId);
            }
            if (null != nativeLanguage) {
                newList = getMatchMateListByNativeLanguage(false, newList, nativeLanguage, currentUserStudentId);
            }
            if (null != favouriteFood) {
                newList = getMatchMateListByFavoriteFood(false, newList, favouriteFood, currentUserStudentId);
            }
            if (null != suburb) {
                newList = getMatchMateListBySuburb(false, newList, suburb, currentUserStudentId);
            }
        } else {
            if (null != courseCode) {
                newList = getMatchResultByCourseCode(true, null, courseCode, currentUserStudentId);
                if (null != nativeLanguage) {
                    newList = getMatchMateListByNativeLanguage(false, newList, nativeLanguage, currentUserStudentId);
                }
                if (null != favouriteFood) {
                    newList = getMatchMateListByFavoriteFood(false, newList, favouriteFood, currentUserStudentId);
                }
                if (null != suburb) {
                    newList = getMatchMateListBySuburb(false, newList, suburb, currentUserStudentId);
                }
            } else {
                if (null != nativeLanguage) {
                    newList = getMatchMateListByNativeLanguage(true, null, nativeLanguage, currentUserStudentId);
                    if (null != favouriteFood) {
                        newList = getMatchMateListByFavoriteFood(false, newList, favouriteFood, currentUserStudentId);
                    }
                    if (null != suburb) {
                        newList = getMatchMateListBySuburb(false, newList, suburb, currentUserStudentId);
                    }
                } else {
                    if (null != favouriteFood) {
                        newList = getMatchMateListByFavoriteFood(true, null, favouriteFood, currentUserStudentId);
                        if (null != suburb) {
                            newList = getMatchMateListBySuburb(false, newList, suburb, currentUserStudentId);
                        }
                    } else {
                        newList = getMatchMateListBySuburb(true, null, suburb, currentUserStudentId);
                    }
                }
            }
        }

        result.setMatchMateList(newList);
        return result;
    }

    private List<MatchMate> getMatchMateListBySuburb(boolean searchFromDatabase, List<MatchMate> existsList, String suburb, Integer currentUserStudentId) {
        List<MatchMate> newList = new ArrayList<MatchMate>();
        if (searchFromDatabase) {
            Query query = getEntityManager().createNamedQuery("Student.findBySuburb");
            query.setParameter("suburb", suburb);
            List<Student> studentList = query.getResultList();
            if (!studentList.isEmpty()) {
                for (Student student : studentList) {
                    if (student.getStudentId() != currentUserStudentId) {
                        MatchMate newMatchMate = new MatchMate();
                        newMatchMate.setStudent(student);
                        newMatchMate.setUnits((List<Unit>) findAllUnitsByStudentId(String.valueOf(student.getStudentId())));
                        newMatchMate.setCourses((List<Course>) findAllCoursesByStudentId(String.valueOf(student.getStudentId())));
                        if (attributeIsPublic(SUBURB, newMatchMate.getStudentSettings())) {
                            newList.add(newMatchMate);
                        }
                    }
                }
            }
        } else {
            if (existsList.size() > 0) {
                for (MatchMate mate : existsList) {
                    if (mate.getStudent().getSuburb().equalsIgnoreCase(suburb) && attributeIsPublic(SUBURB, mate.getStudentSettings())) {
                        newList.add(mate);
                    }
                }
            }
        }
        return newList;
    }

    private List<MatchMate> getMatchMateListByFavoriteFood(boolean searchFromDatabase, List<MatchMate> existsList, String favouriteFood, Integer currentUserStudentId) {
        List<MatchMate> newList = new ArrayList<MatchMate>();
        if (searchFromDatabase) {
            Query query = getEntityManager().createNamedQuery("Student.findByFavouriteFood");
            query.setParameter("favouriteFood", favouriteFood);
            List<Student> studentList = query.getResultList();
            if (!studentList.isEmpty()) {
                for (Student student : studentList) {
                    if (student.getStudentId() != currentUserStudentId) {
                        MatchMate newMatchMate = new MatchMate();
                        newMatchMate.setStudent(student);
                        newMatchMate.setUnits((List<Unit>) findAllUnitsByStudentId(String.valueOf(student.getStudentId())));
                        newMatchMate.setCourses((List<Course>) findAllCoursesByStudentId(String.valueOf(student.getStudentId())));
                        if (attributeIsPublic(FAVOURITE_FOOD, newMatchMate.getStudentSettings())) {
                            newList.add(newMatchMate);
                        }
                    }
                }
            }
        } else {
            if (existsList.size() > 0) {
                for (MatchMate mate : existsList) {
                    if (mate.getStudent().getFavouriteFood().equalsIgnoreCase(favouriteFood) && attributeIsPublic(FAVOURITE_FOOD, mate.getStudentSettings())) {
                        newList.add(mate);
                    }
                }
            }
        }
        return newList;
    }

    private List<MatchMate> getMatchMateListByNativeLanguage(boolean searchFromDatabase, List<MatchMate> existsList, String nativeLanguage, Integer currentUserStudentId) {
        List<MatchMate> newList = new ArrayList<MatchMate>();
        if (searchFromDatabase) {
            Query query = getEntityManager().createNamedQuery("Student.findByNativeLanguage");
            query.setParameter("nativeLanguage", nativeLanguage);
            List<Student> studentList = query.getResultList();
            if (!studentList.isEmpty()) {
                for (Student student : studentList) {
                    if (student.getStudentId() != currentUserStudentId) {
                        MatchMate newMatchMate = new MatchMate();
                        newMatchMate.setStudent(student);
                        newMatchMate.setUnits((List<Unit>) findAllUnitsByStudentId(String.valueOf(student.getStudentId())));
                        newMatchMate.setCourses((List<Course>) findAllCoursesByStudentId(String.valueOf(student.getStudentId())));
                        newMatchMate.setStudentSettings(findAllSettingsByStudentId(student.getStudentId()));
                        if (attributeIsPublic(NATIVE_LANGUAGE, newMatchMate.getStudentSettings())) {
                            newList.add(newMatchMate);
                        }
                    }
                }
            }
        } else {
            if (existsList.size() > 0) {
                for (MatchMate mate : existsList) {
                    if (mate.getStudent().getNativeLanguage().equalsIgnoreCase(nativeLanguage) && attributeIsPublic(NATIVE_LANGUAGE, mate.getStudentSettings())) {
                        newList.add(mate);
                    }
                }
            }
        }
        return newList;
    }
    
    private boolean attributeIsPublic(String attribute, List<StudentSetting> settingList) {
        for (StudentSetting setting : settingList) {
            if (setting.getStudentSettingPK().getAttribute().equals(attribute) && !setting.getRestricted()) {
                return true;
            }
        }
        return false;
    }

    private List<MatchMate> getMatchMateListByUnitCode(MatchCriteria criteria, List<MatchMate> newList) throws NumberFormatException {
        Query query;
        query = getEntityManager().createNamedQuery("StudentUnit.findAllByUnitCode");
        query.setParameter("unitCode", criteria.getUnitCodes());
        List<StudentUnit> studentUnitList = query.getResultList();
        if (!studentUnitList.isEmpty()) {
            Query studentQuery = getEntityManager().createNamedQuery("Student.findByStudentId");
            for (StudentUnit studentUnit : studentUnitList) {
                if (studentUnit.getStudentUnitPK().getStudentId() != Integer.valueOf(criteria.getCurrentUserStudentId())) {
                    MatchMate newMatchMate = new MatchMate();
                    studentQuery.setParameter("studentId", studentUnit.getStudentUnitPK().getStudentId());
                    Student student = (Student) studentQuery.getResultList().get(0);
                    newMatchMate.setStudent(student);
                    newMatchMate.setUnits((List<Unit>) findAllUnitsByStudentId(String.valueOf(student.getStudentId())));
                    newMatchMate.setCourses((List<Course>) findAllCoursesByStudentId(String.valueOf(student.getStudentId())));
                    newMatchMate.setStudentSettings(findAllSettingsByStudentId(student.getStudentId()));
                    newList.add(newMatchMate);
                }
            }
        }
        return newList;
    }
    
    private List<StudentSetting> findAllSettingsByStudentId(Integer studentId) {
        Query studentSettingQuery = getEntityManager().createNamedQuery("StudentSetting.findByStudentId");
        studentSettingQuery.setParameter("studentId", studentId);
        return (List<StudentSetting>)studentSettingQuery.getResultList();
    }

    private List<MatchMate> getMatchResultByCourseCode(boolean searchFromDatabase, List<MatchMate> existsList, String courseCode, Integer currentUserStudentId) {
        List<MatchMate> newList = new ArrayList<MatchMate>();
        if (searchFromDatabase) {
            Query query = getEntityManager().createNamedQuery("StudentCourse.findByCourseCode");
            query.setParameter("courseCode", courseCode);
            List<StudentCourse> studentCourseList = query.getResultList();
            if (!studentCourseList.isEmpty()) {
                Query studentQuery = getEntityManager().createNamedQuery("Student.findByStudentId");
                for (StudentCourse studentCourse : studentCourseList) {
                    int studentId = studentCourse.getStudentCoursePK().getStudentId();
                    if (studentId != currentUserStudentId) {
                        MatchMate newMatchMate = new MatchMate();
                        studentQuery.setParameter("studentId", studentId);
                        Student student = (Student) studentQuery.getResultList().get(0);
                        newMatchMate.setStudent(student);
                        newMatchMate.setUnits((List<Unit>) findAllUnitsByStudentId(String.valueOf(student.getStudentId())));
                        newMatchMate.setCourses((List<Course>) findAllCoursesByStudentId(String.valueOf(student.getStudentId())));
                        newMatchMate.setStudentSettings(findAllSettingsByStudentId(student.getStudentId()));
                        newList.add(newMatchMate);
                    }
                }
            }
        } else {
            if (existsList.size() > 0) {
                for (MatchMate mate : existsList) {
                    for (Course course : mate.getCourses()) {
                        if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                            newList.add(mate);
                            break;
                        }
                    }
                }
            }
        }
        return newList;
    }

    public void createProfile(Profile profile) {
        EntityManager em = getEntityManager();
        Integer studentId = profile.getStudent().getStudentId();

        Query query = em.createNamedQuery("UserAccess.findById");
        query.setParameter("id", Integer.valueOf(profile.getUserId()));
        UserAccess existUser = (UserAccess) query.getSingleResult();
        existUser.setStudentId(studentId);
        em.merge(existUser);
        //save selected courses
        for (String courseCode : profile.getSelectedCourseCodes()) {
            StudentCourse newStudentCourse = new StudentCourse(
                    new StudentCoursePK(studentId, courseCode));
            em.persist(newStudentCourse);
        }
        //save selected units
        for (String unitCode : profile.getSelectedUnitCodes()) {
            StudentUnit newStudentUnit = new StudentUnit(
                    new StudentUnitPK(studentId, unitCode));
            em.persist(newStudentUnit);
        }
        saveDefaultStudentSettings(em, studentId);
        em.persist(profile.getStudent());
    }
    
    public void updateProfile(Profile profile) {
        Query query = getEntityManager().createNamedQuery("Student.findByStudentId");
        query.setParameter("studentId", profile.getStudent().getStudentId());
        Student student = (Student) query.getResultList().get(0);
        student.setNickname(profile.getStudent().getNickname());
        System.out.println("nickname: " + profile.getStudent().getNickname());
        student.setFavouriteFood(profile.getStudent().getFavouriteFood());
        student.setNativeLanguage(profile.getStudent().getNativeLanguage());
        student.setSuburb(profile.getStudent().getSuburb());
        getEntityManager().merge(student);
    }
    
    public void updateStudentSetting(Profile profile) {
        Query query = getEntityManager().createNamedQuery("StudentSetting.findBySettingPK");
        //query.setParameter("id", Integer.valueOf(profile.getUserId()));
        for (StudentSetting newSetting: profile.getStudentSettings()) {
            query.setParameter("studentSettingPK", newSetting.getStudentSettingPK());
            StudentSetting existsSetting = (StudentSetting)query.getResultList().get(0);
            existsSetting.setRestricted(newSetting.getRestricted());
            getEntityManager().merge(existsSetting);
        }
    } 

    public Profile getProfile(String studentId) {
        Profile newProfile = new Profile();
        newProfile.setUserId(studentId);
        Query studentQuery = getEntityManager().createNamedQuery("Student.findByStudentId");
        studentQuery.setParameter("studentId", Integer.valueOf(studentId));
        newProfile.setStudent((Student) studentQuery.getResultList().get(0));
        List<Unit> selectedUnits = (List<Unit>) findAllUnitsByStudentId(studentId);
        List<Course> selectedCourses = (List<Course>) findAllCoursesByStudentId(studentId);
        for (Unit unit : selectedUnits) {
            newProfile.getSelectedUnitCodes().add(unit.getUnitCode());
        }
        for (Course course : selectedCourses) {
            newProfile.getSelectedCourseCodes().add(course.getCourseCode());
        }
        Query studentSettingQuery = getEntityManager().createNamedQuery("StudentSetting.findByStudentId");
        studentSettingQuery.setParameter("studentId", Integer.valueOf(studentId));
        newProfile.setStudentSettings((List<StudentSetting>) studentSettingQuery.getResultList());
        return newProfile;
    }

    private void saveDefaultStudentSettings(EntityManager em, Integer studentId) {
        StudentSetting studentIdSetting = new StudentSetting(
                new StudentSettingPK(studentId, "STUDENT_ID"));
        studentIdSetting.setRestricted(false);
        studentIdSetting.setChanged(false);
        StudentSetting studentNameSetting = new StudentSetting(
                new StudentSettingPK(studentId, "NAME"));
        studentNameSetting.setRestricted(false);
        studentNameSetting.setChanged(false);
        StudentSetting studentNicknameSetting = new StudentSetting(
                new StudentSettingPK(studentId, "NICKNAME"));
        studentNicknameSetting.setRestricted(false);
        studentNicknameSetting.setChanged(true);
        StudentSetting studentLatitudeSetting = new StudentSetting(
                new StudentSettingPK(studentId, "LATITUDE"));
        studentLatitudeSetting.setRestricted(false);
        studentLatitudeSetting.setChanged(false);
        StudentSetting studentLongitudeSetting = new StudentSetting(
                new StudentSettingPK(studentId, "LONGITUDE"));
        studentLongitudeSetting.setRestricted(false);
        studentLongitudeSetting.setChanged(false);
        StudentSetting studentNativeLanguageSetting = new StudentSetting(
                new StudentSettingPK(studentId, "NATIVE_LANGUAGE"));
        studentNativeLanguageSetting.setRestricted(false);
        studentNativeLanguageSetting.setChanged(true);
        StudentSetting studentFavouriteFoodSetting = new StudentSetting(
                new StudentSettingPK(studentId, "FAVOURITE_FOOD"));
        studentFavouriteFoodSetting.setRestricted(false);
        studentFavouriteFoodSetting.setChanged(true);
        StudentSetting studentSuburbSetting = new StudentSetting(
                new StudentSettingPK(studentId, "SUBURB"));
        studentSuburbSetting.setRestricted(false);
        studentSuburbSetting.setChanged(true);

        em.persist(studentIdSetting);
        em.persist(studentNameSetting);
        em.persist(studentNicknameSetting);
        em.persist(studentLatitudeSetting);
        em.persist(studentLongitudeSetting);
        em.persist(studentNativeLanguageSetting);
        em.persist(studentFavouriteFoodSetting);
        em.persist(studentSuburbSetting);
    }
}
