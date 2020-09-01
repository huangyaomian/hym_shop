package com.hym.shop.service.receiver;

public interface MyInstallListener {

    void PackageAdded(String packageName);
    void PackageRemoved(String packageName);
    void PackageReplaced(String packageName);

}
