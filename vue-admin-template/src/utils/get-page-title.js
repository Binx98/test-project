import defaultSettings from '@/settings'

const title = defaultSettings.title || '慧美服装销售管理系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
