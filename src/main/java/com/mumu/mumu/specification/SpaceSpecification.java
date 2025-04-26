//package com.mumu.mumu.specification;
//
//import com.mumu.mumu.domain.Space;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.List;
//
//public class SpaceSpecification {
//
//    public static Specification<Space> hasRegionIn(List<String> regions) {
//        return (root, query, cb) -> regions == null ? null : root.get("region").in(regions);
//    }
//
//    public static Specification<Space> hasTargetIn(List<String> targets) {
//        return (root, query, cb) -> targets == null ? null : root.get("spaceTarget").in(targets);
//    }
//
//    public static Specification<Space> hasTypeIn(List<String> types) {
//        return (root, query, cb) -> types == null ? null : root.get("spaceType").in(types);
//    }
//}
//
